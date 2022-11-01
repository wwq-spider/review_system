package com.review.manage.intake.service;

import com.review.manage.intake.vo.IntakeVo;
import org.jeecgframework.core.common.service.CommonService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author javabage
 * @date 2022/7/18
 */
public interface IntakeService extends CommonService {

    void handleOptions(ModelAndView modelAndView);

    List<IntakeVo> getIntakeInfo(IntakeVo intakeVo);
}
