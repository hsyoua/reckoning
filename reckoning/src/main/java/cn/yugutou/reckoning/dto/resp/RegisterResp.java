package cn.yugutou.reckoning.dto.resp;


import lombok.Data;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.net.URL;
@Data
public class RegisterResp implements Serializable {
    private boolean result;
    public RegisterResp(Boolean result){
        this.result = result;
    }
}
