package io.back3nd.backend.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@RequiredArgsConstructor
public enum StatusCode {

    /* Orders */
    ORDER_SUCCESS(CREATED, "주문이 성공적으로 접수되었습니다."),
    ORDER_FOUND(OK,"주문 조회에 성공헀습니다."),
    ORDER_DELETE(NO_CONTENT,"주문 취소에 성공했습니다."),

    /* Item */
    ITEM_CREATED(CREATED,"상품이 등록 되었습니다."),
    ITEM_FOUND(OK, "상품 조회에 성공했습니다."),
    ITEM_UPDATED(OK, "상품 정보가 변경되었습니다."),
    ITEM_DELETE(NO_CONTENT,"상품이 삭제되었습니다."),

    /* Users */
    USER_CREATED(CREATED,"회원 가입에 성공하였습니다."),
    USER_FOUND(OK, "회원 조회에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
