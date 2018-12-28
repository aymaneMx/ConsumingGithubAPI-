package lmax.com.cga;


import lmax.com.cga.model.ListRepoData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class ApiClient {
    public static final String BASE_URL = "https://api.github.com/";
    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance() {

        // only for DEBUG'ing
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public interface GetDataService  {
        @GET("/search/repositories")
        Call<ListRepoData> getRepos(
                @Query("q") String q,
                @Query("sort") String sort,
                @Query("order") String order,
                @Query( "page" ) int page
        );
    }
}
