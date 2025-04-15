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
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    ),
    VideoItemViewModel(
        id = 2,
        title = "이모티콘",
        thumbnailUrl = "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/1.%207280203073685884161.mp4"
    ),
    VideoItemViewModel(
        id = 3,
        title = "레이저",
        thumbnailUrl = "https://images.pexels.com/photos/1037992/pexels-photo-1037992.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800604.us.archive.org/28/items/electricsheep-flock-248-25000-4/00248%3D25454%3D22828%3D24046.mp4"
    ),
    VideoItemViewModel(
        id = 4,
        title = "카메라 연출",
        thumbnailUrl = "https://images.pexels.com/photos/461940/pexels-photo-461940.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/2.%207220748899554725126.mp4"
    ),
    VideoItemViewModel(
        id = 5,
        title = "비싼 카메라",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/3.%207174920386700643611.mp4"
    ),
    VideoItemViewModel(
        id = 6,
        title = "레터링",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/camera-photography-capcut-template/4.%207239823951822703874.mp4"
    ),
    VideoItemViewModel(
        id = 7,
        title = "레이저",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/1.%206988481571284356354.mp4"
    ),
    VideoItemViewModel(
        id = 8,
        title = "영상 편집",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/4.%206985124080781413634.mp4"
    ),
    VideoItemViewModel(
        id = 9,
        title = "영상 편집2",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/4.%206985124080781413634.mp4"
    ),
    VideoItemViewModel(
        id = 10,
        title = "맑은하늘",
        thumbnailUrl = "https://images.pexels.com/photos/1323550/pexels-photo-1323550.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        videoUrl = "https://ia800505.us.archive.org/2/items/listen-tome-now/5.%207134164303241858305.mp4"
    )
)