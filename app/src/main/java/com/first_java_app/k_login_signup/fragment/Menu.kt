package com.first_java_app.k_login_signup.fragment

import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.first_java_app.k_login_signup.*
import com.first_java_app.k_login_signup.databinding.FragmentMenuBinding
import com.first_java_app.k_login_signup.viewModel_Adapter.MovieAdapter
import com.first_java_app.k_login_signup.model.Movie
import com.first_java_app.k_login_signup.viewModel_Adapter.HomeVM

class Menu : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var binding : FragmentMenuBinding
    private lateinit var viewModel : HomeVM
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.options_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onStart() {
        super.onStart()
        viewModel.getNowPlaying()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_listview -> {
                val lm = LinearLayoutManager(context)
                binding.rvMenuList.layoutManager = lm
            }
            R.id.action_gridview -> {
                val gm = GridLayoutManager(context,2)
                binding.rvMenuList.layoutManager = gm
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMovieList()
        registerMovieList()
        registerErrorList()
        bottomNavigation()
        openProfile()
    }
    private fun openProfile(){
        binding.ivProfile.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_menuFragment_to_profileFragment)
        }
    }
    private fun setUpMovieList() {
        adapter = MovieAdapter(this)
        val lm = LinearLayoutManager(context)
        binding.rvMenuList.layoutManager = lm
        binding.rvMenuList.adapter = adapter
    }
    private fun registerMovieList() {
        viewModel.movieData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun registerErrorList() {
        viewModel.errEvent.observe(viewLifecycleOwner){
            //show dialog
        }
    }
    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout =layoutInflater.inflate(R.layout.item_clicked_movie,null)
        val tvName = dialogLayout.findViewById<TextView>(R.id.movieName)
        val tvDesc = dialogLayout.findViewById<TextView>(R.id.txtMovieDescription)
        val ivImage = dialogLayout.findViewById<ImageView>(R.id.imgMovie)
        val movie : Movie = adapter.getMovie(position)
        tvName.text = movie.title
        tvDesc.text = movie.overview
        val urlImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(dialogLayout).load(urlImage).into(ivImage)
        with(builder){
            setTitle("${tvName.text}")
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }
    private fun bottomNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.now_playing -> {
                    viewModel.getNowPlaying()
                    true
                }
                R.id.top_rating -> {
                    viewModel.getTopRatedMovie()
                    true
                }
                else -> false
            }

        }
    }
}