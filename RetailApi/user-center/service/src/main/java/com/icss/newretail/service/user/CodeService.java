package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

public interface CodeService {
    ResponseResultPage<UserDictValueDTO> querycodeByName(PageData<CodeChidMessage> pageData);

    ResponseBase saveCode(CodeMessage codeMessage);

    ResponseBase updateCode(CodeMessage codeMessage);

    ResponseBase deleteCode(String uuid);

    ResponseResultPage<UserDictDTO> querycode(PageData<CodeMessage> pageData);

    ResponseBase savecodeclid(CodeChidMessage codeChidMessage);

    ResponseBase updateCodeChild(CodeChidMessage codeChidMessage);

    ResponseBase deleteCodeChild(String uuid);
}
