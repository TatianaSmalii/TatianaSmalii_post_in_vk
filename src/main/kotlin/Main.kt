
data class Post( //Класс для хранения данных
    val id: Int,
    val autorId: Int,
    val autorName: String,
    val content: String,
    val published: Long,
    val comment: String,
    val canPin: Int,
    val canDelite: Int,
    val isFavorite: Int
)
data class Likes ( //Класс для хранения данных
    val count:Int,
    val userLike: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean,
    var likes: Int = 0
)

object WallService {//синглтон object-это класс (база данных) в одном экземпляре который доступен глобально, без конструктора
    //ключевое- это класс в котором заключается логика приложений
    //внешние классы и подклассы не должны иметь возможность для создания нового экземпляра.
    // WallService сервис (служба) отвечающий за Хранение (публикацию) постов
    private var posts = emptyArray<Post>()//создаем массив (тип элементов массива Post)
    private var counterId=0

    fun add(post: Post): Post {
        //добавление нового поста (в качестве параметров принимает :Post и возвращает ):Post
        // оригинальный post

        posts += post.copy(id = counterId ++)
        //добавляем пост к массиву
        //в значениях указываем параметр который нужно изменить-id
        return posts.last() //возращаем последний элемент в массиве, т.е. только что добавленный пост
    }
    fun update (newPost: Post):Boolean {//Обновление поста
        // принимает в параметры Post, возращает Boolean (был пост обновлен или нет)
        // при обновлении перезаписывается id
        for ((index, post) in posts.withIndex()){
            //две переменные
            if (post.id == newPost.id){
                posts [index] = newPost
                return true
            }
        }
        return false
    }
    fun printPosts() {
        posts.forEach { println(it) }
    }
    fun clear (){
        posts= emptyArray()
    }
}

fun main (){

    val post1 = Post(0, 2, "3", "4", 5, "6", 7, 8, 9)
    val post2 = Post(1, 12, "13", "14", 15, "16", 17, 18, 19)
    val post3 = Post(2, 22, "Alex", "24", 25, "26", 27, 28, 29)
    // создаём информацию об обновлении
    val updatePost = Post(1, 32, "Oleg", "34", 35, "36", 37, 38, 39)

    val posts = WallService.add(post1)
    println(posts)
    println()
    WallService.add(post2)
    WallService.add(post3)
    WallService.printPosts()
    println()
    WallService.update(updatePost)
    println()
    WallService.printPosts()
}

