package cn.yugutou.reckoning.dto.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddBillResp implements Serializable {
    private Long billid;
    public AddBillResp(Long billid){
        this.billid = billid;
    }
}
