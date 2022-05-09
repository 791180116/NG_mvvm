package com.ng.demo.app.network

import com.ng.demo.data.model.bean.*
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val HOST_URL = "http://192.144.218.30:28080/"//服务器

        //const val HOST_URL = "http://172.16.2.251:18443/"//王鹏飞本地
        const val HOST_URL_IMG = "http://49.233.203.21:18000/"
    }

    /**
     * 登录
     */
    @POST("sysuser/login")
    suspend fun login(
        @Body info: RequestBody
    ): ApiResponse<UserInfo>


    /**
     * author : NG_crazy
     * time   : 2021/10/26
     * desc   : http://192.144.218.30:28080/swagger-ui.html#!/area-controller/getAreaListUsingGET
     * 接口名称：根据部门id  获取车辆列表
     * 请求类型：POST
     * Header：{'Content-Type': 'application/json'}
     * URL：http://ip:port/vehicle/getVinByDeptId
     * 输入参数：
     * 字段	类型	是否必填	说明
     * deptId	String	是	根据部门id,登录返回的
     */
    @GET("vehicle/getVinByDeptId")
    suspend fun getVinByDeptId(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<String>>


    @GET("/vehicle/getVinsByDeptId")
    suspend fun getVinsByDeptId(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<String>>

    @GET("vehicle/getVehicleCurrentInfoList")
    suspend fun getVehicleCurrentInfoList(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<VehicleListBean>>


    /**
     * author   : NG_crazy
     * time     : 2021/10/26
     * desc     :http://192.144.218.30:28080/swagger-ui.html#!/commond-controller/setCtrol_grpc2UsingGET
     * 控制车辆（TBox）(通过gRpc直接调用网关)
     * controlId参数（param参数）
     */
    @GET("commond/setCtrol/v2")
    suspend fun setControlV2(
        @Query("carId") carId: String,
        @Query("controlId") controlId: String,
        @Query("param") param: String
    ): ApiResponse<Any>

    /**
     * 根据vin获取车辆所有信息
     */
    @GET("vehicle/getLatestVehicleDataByVinForTest")
    suspend fun getLatestBehicleDataByVin(@Query("vin") vin: String): ApiResponse<VehicleDataBean>

    /*车辆列表  /vehicle/getVehicleByDeptId
    默认任务（命令）列表   /dispatchTask/getDispatchTask
    正在执行的任务列表  /vehicleTask/getVehicleTask
    创建任务  /vehicleTask/addVehicleTask*/
    /**
     * author   : NG_crazy
     * time     : 2022/01/17
     * desc     : http://192.144.218.30:28080/swagger-ui.html#!/vehicle-controller/getVehicleByDeptIdUsingGET_1
     * 获取部门下的车辆
     */
    @GET("/vehicle/getVehicleByDeptId")
    suspend fun getVehicleByDeptId(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<CarBean>>


    /**
     * author   : NG_crazy
     * time     : 2022/01/17
     * desc     : http://192.144.218.30:28080/swagger-ui.html#!/vehicle-task-controller/getVehicleTaskUsingGET
     * 获取车辆任务列表(正在执行的任务列表)
     */
    @GET("/vehicleTask/getVehicleTask")
    suspend fun getVehicleTask(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<TaskBean>>


    /**
     * author   : NG_crazy
     * time     : 2022/01/17
     * desc     : http://192.144.218.30:28080/swagger-ui.html#/dispatch-task-controller
     * 默认任务（命令）列表
     */
    @GET("/dispatchTask/getDispatchTask")
    suspend fun getDispatchTask(
        @Query("deptId") deptId: String
    ): ApiResponse<ArrayList<CreateTaskBean>>

    /**
     * author   : NG_crazy
     * time     : 2022/01/17
     * desc     : http://192.144.218.30:28080/swagger-ui.html#!/vehicle-task-controller/addVehicleTaskUsingPOST
     * 创建任务
     */
    @POST("/vehicleTask/addVehicleTask")
    suspend fun addVehicleTask(
        @Body info: RequestBody
    ): ApiResponse<Any?>

    /**
     * author   : NG_crazy
     * time     : 2022/01/20
     * desc     : http://192.144.218.30:28080/swagger-ui.html#!/vehicle-task-controller/modifyVehicleTaskUsingPOST
     * 修改车辆任务状态
     * status:执行状态  0未开始  1执行中  2暂停  3终止  4完成
     */
    @POST("/vehicleTask/modifyVehicleTask")
    suspend fun modifyVehicleTask(
        @Body info: RequestBody
    ): ApiResponse<Any?>

    /**
     * 注册
     */
    /*@FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String, @Field("password") pwd: String, @Field(
            "repassword"
        ) rpwd: String
    ): ApiResponse<Any>*/

    /**
     * 获取banner数据
     */
    //@GET("banner/json")
    //suspend fun getBanner(): ApiResponse<ArrayList<BannerResponse>>


    /**
     * 获取首页文章数据
     */
    /*@GET("article/list/{page}/json")
    suspend fun getAritrilList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>*/


    /**
     * 根据分类id获取项目数据
     */
    /*@GET("project/list/{page}/json")
    suspend fun getProjecDataByType(
        @Path("page") pageNo: Int,
        @Query("cid") cid: Int
    ): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>*/

    /**
     * 收藏网址
     */
    /*@POST("lg/collect/addtool/json")
    suspend fun collectUrl(
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResponse<CollectUrlResponse>*/

    /**
     * 添加文章
     */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun addAriticle(
        @Field("title") title: String,
        @Field("link") content: String
    ): ApiResponse<Any?>

    /**
     * 删除一个TODO
     */
    @POST("/lg/todo/delete/{id}/json")
    suspend fun deleteTodo(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 完成一个TODO
     */
    @POST("/lg/todo/done/{id}/json")
    @FormUrlEncoded
    suspend fun doneTodo(@Path("id") id: Int, @Field("status") status: Int): ApiResponse<Any?>


}