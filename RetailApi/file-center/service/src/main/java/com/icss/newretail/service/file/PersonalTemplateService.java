package com.icss.newretail.service.file;


import com.alibaba.fastjson.JSONObject;
import com.icss.newretail.model.*;

public interface PersonalTemplateService {

    public ResponseBaseToken getToken(GetTokenDTO tokenDTO);

    public String accesscy(AccesscyDTO param);

    public ResponseResult<PersonalTemplateTypeDTO> queryTemplateTypeList(String OutsideGoodsID);

    public ResponseRecords<PersonalTemplateListDTO> queryTemplateList(PageData<PersonalTemplateParamDTO> param);

    public ResponseResult<JSONObject> queryTemplateDetail(String GoodsTemplateID);

    public ResponseResult<JSONObject> addTemplateOrder(AddOrderDTO param);

    public ResponseResult<JSONObject> cancelTemplateOrder(AddOrderDTO param);
    
    public ResponseResult<String> submitTemplateOrder(SubmitOrderDTO param);

}
