package com.ys.player.simple.media

data class VideoItemViewModel(
    val id: Long,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
)

val sampleVideoItemViewModels = listOf(
    VideoItemViewModel(
        id = 1,
        title = "크롬캐스트",
        thumbnailUrl = "https://image.idus.com/image/files/cceee06cd12f45c2b12ed5818928d7af_400.jpg",
        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    ),
    VideoItemViewModel(
        id = 2,
        title = "이모티콘",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/3fe049ec8d51410992ebd601505a957f_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/1.%207280203073685884161.mp4"
    ),
    VideoItemViewModel(
        id = 3,
        title = "레이저",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/c56dabeb04144b559a9d3fdf11592116_400.jpg",
        videoUrl = "https://ia800604.us.archive.org/28/items/electricsheep-flock-248-25000-4/00248%3D25454%3D22828%3D24046.mp4"
    ),
    VideoItemViewModel(
        id = 4,
        title = "카메라 연출",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/c56dabeb04144b559a9d3fdf11592116_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/2.%207220748899554725126.mp4"
    ),
    VideoItemViewModel(
        id = 5,
        title = "비싼 카메라",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/c56dabeb04144b559a9d3fdf11592116_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/3.%207174920386700643611.mp4"
    ),
    VideoItemViewModel(
        id = 6,
        title = "레터링",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/c7ff54e8885e4649808c6049ddbaa169_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/4.%207239823951822703874.mp4"
    ),
    VideoItemViewModel(
        id = 7,
        title = "레이저",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/ad995708754a4bf388f1d5a0048dced0_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/1.%206988481571284356354.mp4"
    ),
    VideoItemViewModel(
        id = 8,
        title = "영상 편집",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/27c5f335850447c6afd8904aa8e5f68b_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/4.%206985124080781413634.mp4"
    ),
    VideoItemViewModel(
        id = 9,
        title = "영상 편집2",
        thumbnailUrl = "https://image.qa.id-us.me/image/files/c56dabeb04144b559a9d3fdf11592116_400.jpg",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/4.%206985124080781413634.mp4"
    ),
    VideoItemViewModel(
        id = 10,
        title = "맑은하늘",
        thumbnailUrl = "https://image.idus.com/image/files/58019b175ea64751a91ee6a0d194bdb3_400.png",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/5.%207134164303241858305.mp4"
    )
)