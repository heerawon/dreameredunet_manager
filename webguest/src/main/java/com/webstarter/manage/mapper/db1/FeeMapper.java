package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.FeeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeeMapper {
    List<FeeModel> selectFeeList();
    int insertFee (FeeModel feeModel);
}
