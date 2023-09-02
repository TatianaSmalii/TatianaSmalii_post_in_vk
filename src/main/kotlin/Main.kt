fun main (){
    val post1 = Post(
        0,
        2,
        "3",
        "4",
        5,
        "6",
        7,
        8,
        9,
        attachments = arrayOf(PhotoAttachment("photo",Photo(1, 2," ")),
                              VideoAttachment("Video", Video (2,5,"Summer", 50))))
    val posts = WallService.add (post1)

    // создаём информацию об обновлении
    val updatePost = Post(0, 32, "Oleg", "34", 35, "36", 37, 38, 39, attachments = arrayOf(PhotoAttachment("photo",Photo(1, 2," ")),
        VideoAttachment("Video", Video (2,5,"Winter", 100))))

    println(posts)
    println(updatePost)


}

object WallService {//синглтон object-это класс (база данных) в одном экземпляре который доступен глобально, без конструктора
    //ключевое- это класс в котором заключается логика приложений
    //внешние классы и подклассы не должны иметь возможность для создания нового экземпляра.
    // WallService сервис (служба) отвечающий за Хранение (публикацию) постов
    private var posts = emptyArray<Post>()//создаем массив (тип элементов массива Post)
    private var counterId=0
    fun add(post: Post): Post {
        //добавление нового оригинального post (в качестве параметров принимает :Post и возвращает ):Post

        posts += post.copy(id = counterId ++)
        //добавляем пост к массиву posts
        //в значениях указываем параметр который нужно изменить-id
        return posts.last() //возращаем последний элемент в массиве, т.е. только что добавленный пост
    }
    fun update (newPost: Post):Boolean {//Обновление поста // при обновлении перезаписывается id
        // принимает в параметры Post, возращает Boolean (был пост обновлен или нет)

        for ((index, post) in posts.withIndex()){
            //две переменные index, post
            if (post.id == newPost.id){
                posts [index] = newPost
                return true
            }
        }
        return false
    }
    fun printPosts(posts: Post) {
        this.posts.forEach { println(it) }
    }
    fun clear (){//очистка массива (для автотестов)
        posts= emptyArray()
        counterId =0
    }
}





data class Post( //Класс для хранения данных
    val id: Int? = null,//Nullable тип, т.е. это тип в котором может быть/не быть значения
    val autorId: Int,
    val autorName: String,
    val content: String,
    val published: Long,
    val comment: String,//есть ли комментарии
    val canPin: Int,
    val canDelite: Int,
    val isFavorite: Int,
    //ООП задача №2 сделать поле attachments (+доп поля Nullable (они с ?)
    // Также сделать Attachmen либо интерфейсом/абстрактным классом)
    val attachments: Array <Attachment> = emptyArray()//-свойства (emptyArray- пустой массив по умолчанию)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (!attachments.contentEquals(other.attachments)) return false

        return true
    }

    override fun hashCode(): Int {
        return attachments.contentHashCode()
    }
}

data class Likes ( //Класс для хранения данных
    val count:Int,
    val userLike: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean,
    var likes: Int = 0
)
interface Attachment { //1  //добавление интерфейса Attachment (свойства поста)
    val type: String
}
data class PhotoAttachment ( //2
    override val type: String = "Photo", //переопределение свойства type (тип)//2.3
    val photo:Photo//2.2
): Attachment {//наследуется от Attachment
}
data class Photo(//3
    val id: Int,
    val ownerId: Int,
    val link: String, //ссылка на картинку)
)

data class VideoAttachment (
    override val type: String = "Video",
    val video: Video
):Attachment{//наследуется от Attachment
}
data class Video (
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int //Продолжительность видео
)







