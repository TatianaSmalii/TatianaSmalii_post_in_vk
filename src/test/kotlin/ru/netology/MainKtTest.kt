package ru.netology

import Photo
import Post
import Video
import WallService
import org.junit.After

import org.junit.Assert
import org.junit.Test


class WallServiceTest {
    @Test
    fun add (){
        //val service = WallService
        //service.add(Post(0, 2, "3", "4", 5, "6", 7, 8, 9))
        //val result= WallService.printPosts()

        val result = WallService.add(Post(0, 2, "3", "4", 5, "6", 7, 8, 9))
        //val result1 = WallService.add(Post(1, 2, "3", "4", 5, "6", 7, 8, 9))
        // создание провального автотеста(добавила еще один пост (в массиве-1, соответственно id не совпал)

        Assert.assertEquals(0, result.id)
    }
    @After
    //очистка после того как тест выполнится
    //можно вместо After(после) прописать Before(перед)
    fun clearAfterTest (){
        WallService.clear()

    }
    @Test
    fun updateExisting() {
        //Обновление существующих постов
        // создаём целевой сервис
        val service = WallService
        // заполняем несколькими постами
        service.add(Post(0, 2, "3", "4", 5, "6", 7, 8, 9))
        service.add(Post(1, 12, "13", "14", 15, "16", 17, 18, 19))
        service.add(Post(2, 22, "23", "24", 25, "26", 27, 28, 29))
        // создаём информацию об обновлении
        val update = Post(2, 32, "33", "34", 35, "36", 37, 38, 39)

        // выполняем целевое действие
        val result = service.update(update)

        // проверяем результат на удачу(используйте assertTrue)
        Assert.assertTrue(result)
        //WallService.clear() убираем так как добавили очистку после тестов clearAfterTest
    }
    @Test
    fun updateNonExisting() {
        //Обновление не существующих постов
        val service= WallService
        service.add(Post(0,2, "3", "3", 4L, "5", 6, 7, 8))
        val update = Post (2, 3, "5", "6", 8, "7", 5, 9, 7)

        // проверяем результат на неудачу(используйте assertFalse)
        val result=service.update(update)
        Assert.assertFalse(result)
        //WallService.clear() убираем так как добавили очистку после тестов clearAfterTest
    }
}