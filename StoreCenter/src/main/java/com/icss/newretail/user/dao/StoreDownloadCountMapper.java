package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.DownloadCountReq;
import com.icss.newretail.model.WarZoneDownloadCountDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yanghu
 * @since 2020-07-07
 */
@Repository
public interface StoreDownloadCountMapper {

    Page<WarZoneDownloadCountDTO> getWarZoneCount(@Param("page") Page<WarZoneDownloadCountDTO> page, @Param("request") DownloadCountReq downloadCountReq);

    Page<WarZoneDownloadCountDTO> getBase(@Param("page") Page<WarZoneDownloadCountDTO> page, @Param("request") DownloadCountReq downloadCountReq);

    Page<WarZoneDownloadCountDTO> getStore(@Param("page") Page<WarZoneDownloadCountDTO> page, @Param("request") DownloadCountReq downloadCountReq);

    List<WarZoneDownloadCountDTO> getWarZoneCount(@Param("request") DownloadCountReq req);

    List<WarZoneDownloadCountDTO> getBase(@Param("request") DownloadCountReq req);

    List<WarZoneDownloadCountDTO> getStore(@Param("request") DownloadCountReq req);

    Integer addCardAmount(@Param("orgSeq") String orgSeq, @Param("type") String type);

    Integer queryOrgSeq(@Param("orgSeq") String orgSeq);

    WarZoneDownloadCountDTO queryStoreCardByOrgSeq(@Param("orgSeq") String orgSeq);

    Integer insertOne(@Param("request") WarZoneDownloadCountDTO warZoneDownloadCountDTO);

    String queryNewActivity();

    Integer addCardAmountAddCardId(@Param("orgSeq") String orgSeq);
}
