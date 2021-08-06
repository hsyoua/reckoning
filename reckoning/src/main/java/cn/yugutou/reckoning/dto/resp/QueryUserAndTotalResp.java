package cn.yugutou.reckoning.dto.resp;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryUserAndTotalResp implements Serializable {
    private List  userInfoList;
    private Long totalNum;
}
