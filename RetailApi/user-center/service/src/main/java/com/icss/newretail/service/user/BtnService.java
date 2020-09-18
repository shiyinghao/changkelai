package com.icss.newretail.service.user;


import com.icss.newretail.model.MenuButtonDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;

public interface BtnService {

    ResponseBase updateBtn(MenuButtonDTO menuButtonDTO);

    ResponseBase deleteBtn(String uuid);

    ResponseResult queryBtn(MenuButtonDTO menuButtonDTO);
}
