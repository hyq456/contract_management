package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.vo.WorkRecordVo;

public interface DingdingService {
     void sendWorkRecord(WorkRecordVo workRecordVo);
}
