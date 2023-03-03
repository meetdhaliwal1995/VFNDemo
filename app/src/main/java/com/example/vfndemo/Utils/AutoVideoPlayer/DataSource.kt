package com.example.vfndemo.Utils.AutoVideoPlayer

data class UserVideo(
    val videoUrl: String,
    val name: String
)

fun dummyVideoUrl(): List<UserVideo>{
    return listOf(
    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/625881e7b441019bddce3884/videos/a7103a0e-d574-4e6d-914a-25331ffc218b_1656509227/a7103a0e-d574-4e6d-914a-25331ffc218b.m3u8", "Amit"),

    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/61b3283278d57a7db283d375/videos/b237e52c-5f94-4a6a-b005-c3ac9917b282_1656650873/b237e52c-5f94-4a6a-b005-c3ac9917b282.m3u8", "Manjit Singh"),

    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/612b8d7b08584579441d3b63/videos/c62f0be7-9619-4e9a-aff5-910fe9343e06_1656059502/c62f0be7-9619-4e9a-aff5-910fe9343e06.m3u8", "Mayank"),

    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/612b8d7b08584579441d3b63/videos/f30904fa-3b95-49e2-abd5-ac975d30ec10_1656060101/f30904fa-3b95-49e2-abd5-ac975d30ec10.m3u8", "Ram"),

    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/612b8d7b08584579441d3b63/videos/44cb5d24-dbe7-4af0-9d0a-c7fa6016d0f3_1656049929/44cb5d24-dbe7-4af0-9d0a-c7fa6016d0f3.m3u8", "Madhur"),

    UserVideo("https://d3uovqy5fgz2nh.cloudfront.net/videos/insta_1654106811/insta.m3u8", "Sourav")
    )
}
