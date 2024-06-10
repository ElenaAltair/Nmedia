package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

var nextId: Long = 10

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Двое застряли в лифте; в ожидании, когда их выпустят, коротают время за разговором.\n" +
                    "— Представляешь, эти судебные приставы совсем обнаглели! Позавчера не выпустили меня в загранку, вчера заблокировали счет на мобильнике. Но они не на такого напали — хрен я им чего заплачу!\n" +
                    "Голос из динамика в кабине:\n" +
                    "— Заплатите, если хотите выйти из лифта.",
            published = "21 мая в 20:50",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            //video = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
        ),
        Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "После свадьбы жена — мужу:\n" +
                    "— А где мы будем жить, у твоих родителей, или у моих?\n" +
                    "— Жить мы будем у твоих родителей. А твои родители — у моих.",
            published = "21 мая в 20:40",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
            //video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Мужик на лошади долго катался и под конец совсем измотался, не удержался на лошади и упал.\n" +
                    "— Да, знал бы где упасть, соломки бы подстелил. — сказал мужик.\n" +
                    "— Да и поели б заодно... — сказала лошадь.",
            published = "21 мая в 20:20",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = null
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "На суде адвокат говорит прокурору:\n" +
                    "— Неужели вы не видите, что подсудимый дебил?\n" +
                    "— Это не повод для его оправдания — дебилы точно такие же люди, как мы с вами.",
            published = "21 мая в 19:50",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = null
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "— Боря, что у вас происходит? Ваша теща уже третий раз за этот месяц отравилась грибами!\n" +
                    "— Ой, это все ее проклятый склероз: она готовит для меня, а потом забывает и сама пробует!",
            published = "21 мая в 19:20",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = null
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Всё моё детство у нас дома жил кот. Когда он умер, родители, чтобы меня не расстраивать, сказали, что он женился и сейчас живёт у своей жены.\n" +
                    "Я в это верила и искренне желала ему счастья..\n",
            published = "21 мая в 19:00",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = null
        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое главное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия – помочь встать на путь роста и начать цепочку перемен. http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 0,
            web = 0,
            views = 0,
            contentOld = "",
            webByMe = false,
            viewsByMe = false,
            video = null
        ),
    )
    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {

        posts = posts.map {
            if (it.id != id) it
            else {
                var like = 0
                if (it.likedByMe) like-- else like++
                it.copy(likedByMe = !it.likedByMe, likes = it.likes + like)
            }
        }
        data.value = posts
    }

    override fun webById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(web = it.web + 1, webByMe = true)
        }
        data.value = posts
    }

    override fun viewsById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(views = it.views + 1, viewsByMe = true)
        }
        data.value = posts
    }


    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "Now",
                    likes = 0,
                    web = 0,
                    views = 0,
                    contentOld = "",
                    webByMe = false,
                    viewsByMe = false,
                    video = null
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(contentOld = it.content, content = post.content)
        }
        data.value = posts

    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun undoEditById(id: Long) {
        posts = posts.map {
            if (it.id != id || it.contentOld.equals("")) it else it.copy(
                content = it.contentOld,
                contentOld = ""
            )
        }
        data.value = posts
    }

    override fun videoById(post: Post) {
        TODO("Not yet implemented")
    }


}