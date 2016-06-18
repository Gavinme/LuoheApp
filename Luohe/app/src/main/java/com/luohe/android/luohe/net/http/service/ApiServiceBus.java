package com.luohe.android.luohe.net.http.service;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.mine.MyCollectListActivity;
import com.luohe.android.luohe.mine.MyShareListActivity;
import com.luohe.android.luohe.mine.WenFengDetailBean;
import com.luohe.android.luohe.mine.his.HisWenFengListActivity;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.model.ArticleDetailBean;
import com.luohe.android.luohe.net.model.AttenBean;
import com.luohe.android.luohe.net.model.LuoheBean;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.net.model.LuoheTimeBean;
import com.luohe.android.luohe.net.model.LuoheWrapBean;
import com.luohe.android.luohe.net.model.RankBean;
import com.luohe.android.luohe.net.model.ReactBean;
import com.luohe.android.luohe.net.model.ReportBean;
import com.luohe.android.luohe.net.model.SystemMessage;
import com.luohe.android.luohe.net.model.WishBean;
import com.luohe.android.luohe.ui.MyWenFengListActivity;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserLoginInfo;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by GanQuan on 16/4/3.
 */
public interface ApiServiceBus {
    /**
     * luohe api
     */
    /**
     * 4.1 风格
     *
     * @return
     */
    @POST("accountServices!lhlStyle.action")
    Observable<Result<List<LuoheTagBean>>> lhlStyle();

    /**
     * 4.2 发布落和令
     *
     * @param fallOrderName
     * @param fallOrderDes
     * @param fallOrderRand
     * @param fallOrderTime
     * @param fallOrderPubState
     * @return
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!publishLhl.action")
    Observable<Result<LuoheBean>> publishLhl(@Field("e.fallOrderName") String fallOrderName,
                                             @Field("e.fallOrderDes") String fallOrderDes, @Field("e.fallOrderRand") int fallOrderRand,
                                             @Field("e.fallOrderTime") int fallOrderTime, @Field("e.fallOrderPubState") int fallOrderPubState);

    @FormUrlEncoded
    @POST("lhlFallOrderServices!closedFallOrder.action")
    Observable<Result> closeLhl(@Field("e.fallOrderId") int fallOrderId);

    /**
     * 4.4 落和令时长
     *
     * @return
     */
    @POST("lhlFallOrderServices!fallOrderTime.action")
    Observable<Result<List<LuoheTimeBean>>> fallOrderTime();

    /**
     * 4.4 落和令 lhlFallOrderServices!myFallOrder.action
     *
     * @return
     */
    @POST("lhlFallOrderServices!myFallOrder.action")
    Observable<Result<List<LuoheWrapBean>>> myfallOrder();

    /**
     * TA的落和
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!fallOrder.action")
    Observable<Result<List<LuoheWrapBean>>> hisFallOrder(@Field("e.fallOrderUserId") int fallOrderUserId);


    /**
     * 落和令列表
     *
     * @param mainStyleId
     * @param fallOrderState
     * @return
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!fallOrder.action")
    Observable<Result<List<LuoheWrapBean>>> fallOrder(@Field("e.mainStyleId") int mainStyleId,
                                                      @Field("e.fallOrderState") String fallOrderState, @Field("pager.offset") int offset);

    /**
     * 所有落和令列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!fallOrder.action")
    Observable<Result<List<LuoheWrapBean>>> allFallOrder(@Field("e.fallOrderState") String fallOrderState);

    /**
     * 4.5 发布主题
     *
     * @param titleName
     * @param titleValue
     * @param titleDes
     * @param artAnonymous
     * @param fallOrderId
     * @return
     */
    @FormUrlEncoded
    @POST("lhlArticleServices!publishSubject.action")
    Observable<Result<Void>> publishSubject(@Field("e.artName") String titleName, @Field("e.artValue") int titleValue,
                                            @Field("e.artDes") String titleDes, @Field("e.artAnonymous") int artAnonymous,
                                            @Field("e.fallOrderId") int fallOrderId);

    // @FormUrlEncoded
    // @POST("myTower")
    // Observable<Result> fallOrder();

    /**
     * 关注
     */
    @FormUrlEncoded
    @POST("focusUser")
    Observable<Result> focusUser();

    /**
     * 4.8 用户基本信息
     */
    @FormUrlEncoded
    @POST("accountServices!userInfo.action")
    Observable<Result<UserCommonInfo>> userInfo(@Field("e.id") int userId);

    /**
     * 4.9 点赞 文章或者评论的赞 1:分享的赞 2:文章的赞 3:评论的赞 4:回复的赞
     */
    @FormUrlEncoded
    @POST("lhlTitleFrontServices!praiseSub.action")
    Observable<Result> praiseSub(@Field("lhlSupportUser.praiseType") int praiseType,
                                 @Field("lhlSupportUser.titleId") int titleId);

    /**
     * 4.10 回复
     */
    @FormUrlEncoded
    @POST("lhlStyleServices!replyUser")
    Observable<Result> replyUser(@Field("reply.replyUserId") int replyUserId, @Field("reply.replyCon") String replyCon);


    /**
     * 4.11 加赏
     */
    @FormUrlEncoded
    @POST("lhlStyleServices!rewardUser")
    Observable<Result> rewardUser(@Field("e.userId") int userId, @Field("e.artId") int artId,
                                  @Field("e.artValue") int artValue);

    /**
     * 4.12 查看文章
     *
     * @param titleId
     * @return
     */
    @FormUrlEncoded
    @POST("lhlArticleTitleServices!artDetail.action")
    Observable<Result<ArticleDetailBean>> artDetail(@Field("e.titleId") int titleId);

    /**
     * todo by fxl  create on 2016/06/07
     * 查看文风详情
     */
    @FormUrlEncoded
    @POST("lhlStyleServices!styleDetail.action")
    Observable<Result<ArticleDetailBean>> seeDetails(@Field("e.styleId") int styleId);

    /**
     * 文风详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("lhlStyleServices!styleDetail.action")
    Observable<Result<WenFengDetailBean>> styleDetail(@Field("e.styleId") int styleId);

    /**
     * 4.13 主题文章评论列表
     */
    @FormUrlEncoded
    @POST("lhlTitleFrontServices!articlComList.action")
    Observable<Result<List<CommentBean>>> articleCommentList(@Field("e.titleId") int id);

    /**
     * 4.14 发布评论lhlTitleFrontServices!publishCom.action
     * <p/>
     * 类型 1. 文章类型 2.主题
     */
    @FormUrlEncoded
    @POST("lhlTitleFrontServices!publishCom.action")
    Observable<Result> publishCom(@Field("comment.titleId") int titleId,
                                  @Field("comment.commentText") String commentText, @Field("comment.comType") int type);

    /**
     * 4.15 评论回复lhlTitleFrontServices!comReply.action
     */

    @FormUrlEncoded
    @POST("lhlTitleFrontServices!comReply.action")
    Observable<Result> comReply(@Field("reply.id") int id, @Field("reply.userId") int userId,
                                @Field("reply.replyCon") String replyCon);

    /**
     * 4.16 收藏
     */
    @FormUrlEncoded
    @POST("lhlCollectionBookServices!collArt.action")
    Observable<Result> collArti(@Field("id") int id, @Field("isCancle") int replyCon, @Field("e.sctype") int sctype);

    /**
     * 4.17 发祝愿
     *
     * @return
     */
    @FormUrlEncoded
    @POST("lhlWishServices!publishWish.action")
    Observable<Result> publishWish(@Field("e.wishUserId") int wishUserId, @Field("e.wishValue") int wishValue,
                                   @Field("e.wishContent") String wishContent, @Field("e.isAnonymous") int isAnonymous);

    /**
     * 4.18 举报类型
     *
     * @return
     */
    @POST("lhlReportFrontServices!comType.action")
    Observable<Result<List<ReportBean>>> comType();

    @FormUrlEncoded
    @POST("lhlReportFrontServices!comArtOrCom.action")
    Observable<Result> comArtOrCom(@Field("e.repArtOrCommentId") int repArtOrCommentId,
                                   @Field("e.repArtOrComment") int repArtOrComment, @Field("e.repType") int repType,
                                   @Field("e.repReson") String repReson);

    /**
     * 4.20 删除文章
     */
    @FormUrlEncoded
    @POST("lhlTitleServices!delArtOrCom.action")
    Observable<Result> delArtOrCom(@Field("artOrCommentId") int artOrCommentId, @Field("artOrComment") int artOrComment);

    /**
     * 4.21 写文章（主题）
     */
    @FormUrlEncoded
    @POST("lhlTitleServices!publishArticl.action")
    Observable<Result> publishArticl(@Field("e.artId") int artId, @Field("e.titleName") int titleName,
                                     @Field("e.titleContent") int titleContent, @Field("isFlag") int isFlag);

    /**
     * 4.22 获取主题信息
     */
    @FormUrlEncoded
    @POST("getTitleInfo")
    Observable<Result> getTitleInfo(@Field("e.id") int id);

    /**
     * 获取手机验证码 accountServices!moblieValidate.action
     */
    @FormUrlEncoded
    @POST("accountServices!moblieValidate.action")
    Observable<Result> mobileValidate(@Field("phone") String phone);

    /**
     * 4.25 注册(第一步)
     *
     * @param phone
     * @param pass
     * @param valCode
     * @param isFlag
     * @return
     */

    @FormUrlEncoded
    @POST("accountServices!regUserOne.action")
    Observable<Result> regUserOne(@Field("phone") String phone, @Field("pass") String pass,
                                  @Field("valCode") String valCode, @Field("isFlag") int isFlag);

    /**
     * 4.26 注册(第二步)
     *
     * @param mainStyleId
     * @param styleIds
     * @return
     */
    @FormUrlEncoded
    @POST(" accountServices!regUserTwo.action")
    Observable<Result> regUserTwo(@Field("mainStyleId") int mainStyleId, @Field("styleIds") int styleIds);

    /**
     * 4.25 登录
     *
     * @param phone
     * @param pass
     * @return
     */
    @FormUrlEncoded
    @POST("accountServices!login.action")
    Observable<Result<UserLoginInfo>> login(@Field("phone") String phone, @Field("pass") String pass);

    /**
     * 4.26 搜索创者
     *
     * @param condition
     * @return
     */
    @FormUrlEncoded
    @POST("accountServices!searchList.action")
    Observable<Result<List<UserCommonInfo>>> searchList(@Field("condition") String condition);

    /**
     * 4.29 搜索文章
     *
     * @param condition
     * @return
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!searchTitleList.action")
    Observable<Result<List<RecommendListItem>>> searchTitleList(@Field("condition") String condition);

    /**
     * 4.30 搜索落和令
     *
     * @param condition
     * @return
     */
    @FormUrlEncoded
    @POST("lhlFallOrderServices!searchList.action")
    Observable<Result<List<LuoheWrapBean>>> searchLuoheList(@Field("condition") String condition);

    /**
     * 4.31 推荐（创者佳作）
     */

    @FormUrlEncoded
    @POST("lhlFallOrderServices!recommendArticle.action")
    Observable<Result<List<RecommendListItem>>> recommendArticle(@Field("e.mainStyleId") int mainStyleId,
                                                                 @Field("e.pageNum") int pageNum);

    /**
     * 4.32 我的问令
     *
     * @return
     */
    @FormUrlEncoded
    @POST("lhlArticleServices!mySubject.action")
    Observable<Result<List<LuoheWrapBean.SubjectBean>>> mySubject(@Field("e.pageNum") int page);

    @FormUrlEncoded
    @POST("lhlArticleServices!subject.action")
    Observable<Result<List<LuoheWrapBean.SubjectBean>>> hisSubject(@Field("e.userId") int userId,
                                                                   @Field("pager.offset") int pageNum);

    /**
     * 4.35 他的文风
     *
     * @param styleUserId
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("lhlStyleServices!style.action")
    Observable<Result<List<HisWenFengListActivity.HisWenFengBean>>> hisStyleServices(@Field("e.styleUserId") int styleUserId,
                                                                                     @Field("pager.offset") int pageNum);

    /**
     * 4.36 我的文风
     */
    @FormUrlEncoded
    @POST("myStyleServices!style.action")
    Observable<Result<List<MyWenFengListActivity.WenFengBean>>> myStyleServices(@Field("e.pageNum") int page);


    /**
     * 4.42发布文风
     *
     * @param styleName
     * @param userId
     * @param styleContent
     * @param styleSubContent
     * @param styleDraft
     * @param styleRand
     * @param file
     * @param fileBase64
     * @return
     */
    @FormUrlEncoded
    @POST("myStyleServices!publishStyle.action")
    Observable<Result> publishStyle(@Field("e.styleName") String styleName, @Field("e.userId") int userId,
                                    @Field("e.styleContent") String styleContent, @Field("e.styleSubContent") String styleSubContent,
                                    @Field("e.styleDraft") int styleDraft, @Field("e.styleRand") String styleRand,
                                    @Field("e.file") String file, @Field("e.fileBase64") String fileBase64);

    /**
     * 我的收藏
     *
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("lhlCollectionBookServices!collectionList.action")
    Observable<Result<List<MyCollectListActivity.CollectBean>>> myCollect(@Field("pager.offset") int pageNum);

    /**
     * 4.43我的分享
     *
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("lhlShareServices!shareList.action")
    Observable<Result<List<MyShareListActivity.ShareBean>>> myShare(@Field("pager.offset") int pageNum);

    //
    // /**
    // * personal api
    // */
    // interface MineApi {
    //
    // }
    //
    // interface RecommendApi {
    //
    // }
    //
    // interface ChatApi {
    //
    //
    // }
    //
    // interface FindApi {
    //
    // }

    // 互动
    @FormUrlEncoded
    @POST("lhlShareServices!eachOtherList.action")
    Observable<Result<List<ReactBean>>> eachOtherList(@Field("e.type") int type);

    // 累计榜
    @FormUrlEncoded
    @POST("lhlAllValueServices!rankByStyleId.action")
    Observable<Result<List<RankBean>>> rankByStyleId(@Field("e.styleId") int type);

    // 百强榜
    @FormUrlEncoded
    @POST("lhlOrderWeekServices!rankByStyleId.action")
    Observable<Result<List<RankBean>>> rankByStyleIdMain(@Field("e.mainStyleId") int type);

    // 好友榜
    @FormUrlEncoded
    @POST("lhlAllValueServices!friendRankList.action")
    Observable<Result<List<RankBean>>> friendRankList(@Field("Id") int id);

    // 祝愿
    @FormUrlEncoded
    @POST("lhlWishServices!wishList.action")
    Observable<Result<List<WishBean>>> wishList(@Field("e.wishType") int type, @Field("e.pageNum") int page);

    // 系统消息
    @FormUrlEncoded
    @POST("lhlSysInfoServices!sysInfoList.action")
    Observable<Result<List<SystemMessage>>> sysInfoList(@Field("Id") int id);

    /**
     * accountSettingServices!changePhoto.action 上传头像
     */
    @FormUrlEncoded
    @Multipart
    @POST("accountSettingServices!changePhoto.action")
    Observable<Result> changePhoto(@Part("fileBase64") RequestBody fileBase64,
                                   @Field("fileFileName") String fileFileName);

    // 好友
    @FormUrlEncoded
    @POST("lhlAttentionServices!attenList.action")
    Observable<Result<List<AttenBean>>> attenList(@Field("e.userState") int state, @Field("e.type") int type,
                                                  @Field("pager.offset") int offset);

    /**
     * accountSettingServices!updateUserInfo.action 个人信息
     */
    @FormUrlEncoded
    @POST("accountSettingServices!updateUserInfo.action")
    Observable<Result> updateUserInfo(@FieldMap ApiParams userCommonInfo);

    // 修改密码
    @FormUrlEncoded
    @POST("accountSettingServices!changePwd.action")
    Observable<Result> changePwd(@Field("e.password") String password, @Field("e.newPassword") String newPassword,
                                 @Field("e.newPassword2") String newPassword2);


}
