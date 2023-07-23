package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;//주문가격

    private int count;//주문 수량
    /*
    //대체로 @NoArgsConstructor(access = AccessLevel.PROTECTED) 이렇게도 많이 사용한다
    protected OrderItem(){
            1. 이렇게 protected 생성자를 만들어주면 다른 곳에서 new OrderItem()으로 못 만든다
            2. 그렇다면 다른곳에서 OrderItem을 만들기 위해서는 createOrderItem()을 사용할 수 밖에 없다.
            3. 이렇게 강제하면 createOrderItem 생성시 필요한 로직을 여기서만 관리할 수 있으니 유지보수가 편리하다

            [중요 - 이렇게는 사용하지 말자]
           OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(count);
    }
    */

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel(){
        getItem().addStock(count);
    }

    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조다
     * @return
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }

}
