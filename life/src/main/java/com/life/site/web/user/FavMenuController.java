package com.life.site.web.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.UserVo;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Date    : 2020. 9. 17.
 * @package : com.hansol.user
 * @file    : FavMenuController.java
 * @Author  : hana.k
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 9. 17. hana.k        최초 생성
 *
 */
@RestController
@RequestMapping("/api/favorites")
public class FavMenuController {
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    FavMenuService favMenuService;
    
    /**
     * 사용자별 즐겨찾기 목록 조회
     * @param request
     * @param param USER_ID
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/list")
    public CommonResult getFavoritesMenu(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        UserVo user = SessionManager.getUser(request);
        Map<String,Object> parameter = param.getData();
        parameter.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        parameter.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());
        parameter.put(CommonConstants.Params.ORG_CD, user.getORG_CD());

        result.setData(favMenuService.getFavoritesMenu(parameter));
        
        return result;
    }

    /**
     * 사용자별 즐겨찾기 메뉴 추가
     * 
     * @param request
     * @param param MENU_CD, MENU_NM, PROGRAM_CD, PROGRAM_URL
     * @return 쿼리 결과(반영된 row 수)
     * @throws Exception
     */
    @PostMapping(value = "/add")
    public CommonResult addFavoritesMenu(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        Map<String,Object> parameter = param.getData();
        
        UserVo user = SessionManager.getUser(request);

        parameter.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        parameter.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());
        parameter.put(CommonConstants.Params.ORG_CD, user.getORG_CD());

        result.setData(favMenuService.addFavoritesMenu(parameter));

        user.setFavoritesMenuList(favMenuService.getFavoritesMenu(parameter));
        SessionManager.setUser(request, user);
        
        return result;
    }

    /**
     * 사용자별 선택한 즐겨찾기 메뉴 삭제
     * @param request
     * @param param MENU_CD
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public CommonResult deleteFavoritesMenu(HttpServletRequest request, CommonParam param) throws Exception {
        Map<String,Object> parameter = param.getData();
        CommonResult result  = new CommonResult();
        UserVo user = SessionManager.getUser(request);

        parameter.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        parameter.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());
        parameter.put(CommonConstants.Params.ORG_CD, user.getORG_CD());
        result.setData(favMenuService.delFavoritesMenu(parameter));

        user.setFavoritesMenuList(favMenuService.getFavoritesMenu(parameter));
        SessionManager.setUser(request, user);

        return result;
    }
}
