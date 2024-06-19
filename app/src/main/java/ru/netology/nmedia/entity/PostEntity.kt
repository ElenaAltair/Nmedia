package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity //(tableName = "MyPosts")
data class PostEntity( // создаёт таблицу в БД на основе этой информации
    @PrimaryKey(autoGenerate = true)
    val id: Long, //@ColumnInfo() эта аннотация позволяет указать более детальную информацию о столбце
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Long = 0,
    val web: Long = 0,
    val views: Long = 0,
    val contentOld: String,
    val webByMe: Boolean,
    val viewsByMe: Boolean,
    val video: String?,

    ) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likedByMe,
        likes,
        web,
        views,
        contentOld,
        webByMe,
        viewsByMe,
        video
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id, dto.author, dto.content, dto.published, dto.likedByMe, dto.likes, dto.web,
                dto.views, dto.contentOld, dto.webByMe, dto.viewsByMe, dto.video
            )

    }
}