package ru.netology.nmedia.activity

import android.R.attr.defaultValue
import android.R.attr.key
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        // получаем аргументы передаваемые из других фрагментов или из внешних источников
        // (т.е. фрагмент умеет принимать текст)
        arguments?.textArg?.let(binding.edit::setText)

        val viewModel: PostViewModel by activityViewModels()

        /* Можно вместо

        // у наших двух фрагметов общая viewModels, т.е. viewModels каждого из них
        // привязана к родительскому фрагменту, который является контроллер навигации, он у них общий
        //(поэтому в FeedFragment мы можем начать редактирование, а в NewPostFragmente редактирование закончить
        // обращение к ViewModel)
                // val viewModel: PostViewModel by viewModels(
                //            ownerProducer = ::requireParentFragment
                //        )

        использовать

                val viewModel: PostViewModel by activityViewModels()

        но тогда надо исправить и в FeedFragment

        Это можно сделать если нужно сделать общую viewModel не только для фрагментов, но и для активити
         */

        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            if (binding.edit.text.isNotBlank()) {

                viewModel.changeContent(binding.edit.text.toString()) // делаем то, что раньше делали в контракте (передаём текст из поля ввода)
                viewModel.save()
                findNavController().navigateUp() //закрыть текущий фрагмент и вернуться к предыдущему
            }
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }

}