package com.demircandemir.artlist.view


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.demircandemir.artlist.R
import com.demircandemir.artlist.adapter.ArtAdapter
import com.demircandemir.artlist.databinding.FragmentArtListBinding
import com.demircandemir.artlist.model.Art


class FragmentArtList : Fragment() {

    private var _binding : FragmentArtListBinding? = null
    private val binding get() = _binding!!
    private lateinit var artList : ArrayList<Art>
    private lateinit var artAdapter: ArtAdapter
    private lateinit var database : SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        artList = ArrayList()

        database = requireActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentArtListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artList.clear()
        artAdapter = ArtAdapter(artList)
        binding.recyclerView.adapter = artAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())



        getFromSql()
    }

    fun updateRecyclerData(newArt : List<Art>){
        artAdapter.setData(newArt)
        artAdapter.notifyDataSetChanged()
    }

    fun getFromSql(){

        try {
            val cursor = database.rawQuery("SELECT * FROM arts", null)
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                Log.d("while", "girdi")
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val art = Art(name, id)
                artList.add(art)
            }



            artAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e : Exception){
            e.printStackTrace()
        }

    }


    // inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.art_menu, menu)
    }

    // handle item clicks of menu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_art_item){
            val action = FragmentArtListDirections.actionFragmentArtListToFragmentDetail(0)
            action.info = "new"
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }
        /*return NavigationUI.
        onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}