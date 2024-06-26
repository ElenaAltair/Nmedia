package ru.netology.nmedia.dto

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onViews(post: Post) {}
    fun onWeb(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onUndoEdit(post: Post) {}
    fun onVideo(post: Post) {}
    fun onImageVideo(post: Post) {}
    fun onContent(post: Post) {}
}