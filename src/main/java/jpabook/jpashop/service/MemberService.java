package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional //<==여기는 (readOnly = false)로 먹게 된다
    public Long join(Member member){
        validateDuplicateMember(member); //유효성 체크
        memberRepository.save(member); //이 부분에서 KEY는 member_id인데 사실 DB에 들어갈때 값이 넣어져야 하지만 그게 아니라 영속성에 의해서 save하기전에 이미 기존에 넘긴 member객체에도 새롭게 추가할 id값을 넣어준다
        return member.getId(); //그래서 저장할때 member에는 member_id값이 없었지만, save할때 자동으로 Id값을 넣어줘서 return을 할 수 있다
    }

    private void validateDuplicateMember(Member member){
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

/*
 * @Transactional을 클래스 위에 쓰게 되면 public으로 선언된 메소드들에게는 모두 자동으로 Transaction 처리가 걸리게 된다
 * Transactional을 사용할 때는 스프링쪽을 사용한다(import org.springframework.transaction.annotation.Transactional;)
 * @Transactional(readOnly = true)을 조회에서 사용하게 되면 성능이 올라가고, 만약 삽입, 수정, 삭제에 사용하게 되면 동작하지 않으니까 주의하자
 *
 */
