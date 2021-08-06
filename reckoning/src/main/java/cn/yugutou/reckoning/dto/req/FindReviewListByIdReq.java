package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import java.io.Serializable;
@Data
public class FindReviewListByIdReq implements Serializable {
    private Long userId;
}
