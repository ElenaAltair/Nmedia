package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWS} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_CONTENT_OLD} TEXT NOT NULL DEFAULT "",
            ${PostColumns.COLUMN_SHARE_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWS_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT DEFAULT NULL
        );
        """.trimIndent()
    }


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {

        var values = ContentValues().apply {
            // TODO: remove hardcoded values
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
            //put(PostColumns.COLUMN_VIDEO, "https://www.youtube.com/watch?v=UO4wBuB-Lxg")
        }

        val id = if (post.id != 0L) {
            values = ContentValues().apply {
                // TODO: remove hardcoded values
                put(PostColumns.COLUMN_CONTENT_OLD, post.contentOld)
                put(PostColumns.COLUMN_CONTENT, post.content)
            }
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_ID} = ?",
                arrayOf(post.id.toString()),
            )
            post.id
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostColumns.TABLE} SET
                    ${PostColumns.COLUMN_LIKES} = ${PostColumns.COLUMN_LIKES} + CASE WHEN ${PostColumns.COLUMN_LIKED_BY_ME} THEN -1 ELSE 1 END,
                    ${PostColumns.COLUMN_LIKED_BY_ME} = CASE WHEN ${PostColumns.COLUMN_LIKED_BY_ME} THEN 0 ELSE 1 END
                WHERE ${PostColumns.COLUMN_ID} = ?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun webByID(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostColumns.TABLE} SET
                    ${PostColumns.COLUMN_SHARE} = ${PostColumns.COLUMN_SHARE} + 1,
                    ${PostColumns.COLUMN_SHARE_BY_ME} = 1
                WHERE ${PostColumns.COLUMN_ID} = ?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewsById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostColumns.TABLE} SET
                    ${PostColumns.COLUMN_VIEWS} = ${PostColumns.COLUMN_VIEWS} + 1,
                    ${PostColumns.COLUMN_VIEWS_BY_ME} = 1
                WHERE ${PostColumns.COLUMN_ID} = ?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun undoEditById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostColumns.TABLE} SET
                    ${PostColumns.COLUMN_CONTENT} = CASE WHEN ${PostColumns.COLUMN_CONTENT_OLD} == "" THEN ${PostColumns.COLUMN_CONTENT} ELSE ${PostColumns.COLUMN_CONTENT_OLD} END,
                    ${PostColumns.COLUMN_CONTENT_OLD} = ""
                WHERE ${PostColumns.COLUMN_ID} = ?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    // функция, которая умеет отображать строку в объекте Post
    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likes = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                web = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE)),
                views = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWS)),
                contentOld = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT_OLD)),
                webByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE_BY_ME)) != 0,
                viewsByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWS_BY_ME)) != 0,
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
            )
        }

    }

}