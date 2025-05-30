package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.users.CreateEmployeeDto
import ua.wied.domain.models.user.User

interface UserApi {

    @GET("api/users/all_employee")
    suspend fun getEmployees(): WrappedResponse<List<User>>

    @POST("api/users/create_employee")
    suspend fun createEmployee(
        @Body dto: CreateEmployeeDto
    ): Response<Any>

    @DELETE("api/users/delete_employee/{employee_id}")
    suspend fun deleteEmployee(
        @Path("employee_id") employeeId: Int
    ): Response<Any>

}