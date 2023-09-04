import javax.xml.stream.events.Comment

fun main (){
    val post1 = Post(0, 2, "3", "4", 5, "6", 7, 8, 9,
        attachments = arrayOf(PhotoAttachment("photo",Photo(1, 2," ")),
                              VideoAttachment("Video", Video (2,5,"Summer", 50))),
        "8", "9", "10", "15", 16, "17", 18, "19", 20, 21, 22, 23, 24, 25)
    val posts = WallService.add (post1)

    // создаём информацию об обновлении
    val updatePost = Post(0, 32, "Oleg", "34", 35, "36", 37, 38, 39,
        attachments = arrayOf(PhotoAttachment("photo",Photo(1, 2," ")),
                            VideoAttachment("Video", Video (2,5,"Winter", 100))),"8",
        "9","10", "15", 16, "17", 18, "19", 20, 21, 2, 23, 24, 25
        )
    val newComment = Comment (1,2,3,"First comment")
    println(WallService.createComment(1,newComment))
    //println( WallService.createComment(2, comment = 1,2, "New comment"  )
    println(newComment)
    //println(updatePost)
    println()

}
object WallService {//синглтон object-это класс (база данных) в одном экземпляре который доступен глобально, без конструктора
    //ключевое- это класс в котором заключается логика приложений
    //внешние классы и подклассы не должны иметь возможность для создания нового экземпляра.
    // WallService сервис (служба) отвечающий за Хранение (публикацию) постов
    private var posts = emptyArray<Post>()//создаем массив (тип элементов массива Post)
    private var counterId=0
    private var comments = emptyArray<Comment>()
    fun add(post: Post): Post {
        //добавление нового оригинального post (в качестве параметров принимает :Post и возвращает ):Post

        posts += post.copy(id = counterId ++)
        //добавляем пост к массиву posts
        //в значениях указываем параметр который нужно изменить-id
        return posts.last() //возращаем последний элемент в массиве, т.е. только что добавленный пост
    }
    fun update (newPost: Post):Boolean {//Обновление поста // при обновлении перезаписывается id
        // принимает в параметры Post, возращает Boolean (был пост обновлен или нет)
        for ((index, post) in posts.withIndex()){ //две переменные index, post
            if (post.id == newPost.id){
                posts [index] = newPost
                return true
            }
        }
        return false
    }
    fun createComment (postId: Int, comment: Comment): Comment {//Задача 3.1. Исключения
    // Первый вариант реализации
        //1 проверить существование поста, если сущ- выведи комментарий, если нет - выдай исключение PostNotFoundException (пост не найден)
//        for ((index, post) in posts.withIndex()){
//            if (post.id == postId){
//                comments += comment
//            }
//            return comment
//        }

    //Второй вариант реализации
        posts.find { it.id == postId } ?: throw PostNotFoundException("В массиве нет поста с id - $postId")
        //найди в постах это id (если оно совпадает с postId, то выведи коммент(последний), если нет/0-исключение
        comments += comment
        return comment
    }
    class PostNotFoundException(s: String) : RuntimeException(s) {
    }
    fun printPosts(posts: Post) {
        this.posts.forEach { println(it) }
    }
    fun clear (){//очистка массива (для автотестов)
        posts= emptyArray()
        counterId =0
    }
}



data class Comment ( //Комментарий
    var id : Int,
    val FromId : Int,
    val date :Int ,
    val text : String
){}

data class Post( //Класс для хранения данных
    val id: Int? = null,//Nullable тип, т.е. это тип в котором может быть/не быть значения
    val autorId: Int,
    val autorName: String,
    val content: String,
    val published: Long,
    val comment: String,
    val canPin: Int,
    val canDelite: Int,
    val isFavorite: Int,
    val attachments: Array <Attachment> = emptyArray(),//-свойства (emptyArray- пустой массив по умолчанию)
    val lat : String,
    val long: String,
    val guid : String,
    val description : String, //текст описания видео
    val canAdd : Int,
    val linkImade : String,
    val spectators : Int, //Кол-во зрителей прямой трансляции
    val title: String,
    val date : Int,
    val views : Int, //Кол-во просмотров видео
    val lokalViews : Int, //кол-во просмотров вконтакте на внешнее видео
    val processing : Int,
    val width : Int, //ширина видео
    val height : Int, //высота видео


    //ООП задача №2 сделать поле attachments (+доп поля Nullable (они с ?)
    // Также сделать Attachmen либо интерфейсом/абстрактным классом)

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







