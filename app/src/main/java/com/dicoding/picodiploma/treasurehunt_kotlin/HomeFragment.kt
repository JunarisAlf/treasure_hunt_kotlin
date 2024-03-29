package com.dicoding.picodiploma.treasurehunt_kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.picodiploma.treasurehunt_kotlin.databinding.ActivityMainBinding
import com.dicoding.picodiploma.treasurehunt_kotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var adapter: HomeBraceAdapter
    private val list = ArrayList<BraceData>()
    private lateinit var dot : ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false) // Inflate the layout for this fragment

        activity?.actionBar?.hide()

        list.add(
            BraceData(
                R.drawable.brace1
            )
        )

        list.add(
            BraceData(
                R.drawable.brace2
            )
        )

        list.add(
            BraceData(
                R.drawable.brace3
            )
        )

        adapter = HomeBraceAdapter(list)
        binding.viewPagerHome.adapter = adapter
        dot = ArrayList()

        setIndicator()

        binding.viewPagerHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedImage(position)
                super.onPageSelected(position)
            }
        })

        val inputCode = binding.inputCode
        val playButton = binding.playButton

        inputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                playButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.login_gray))
                playButton.isClickable = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                playButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))

                play()
            }

            override fun afterTextChanged(s: Editable?) {
                playButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))

                play()
            }

        })

        return binding.root
    }

    private fun play() {
        val inputCode = view?.findViewById<EditText>(R.id.input_code)
        val playButton = view?.findViewById<Button>(R.id.play_button)

        playButton?.setOnClickListener{
            if (inputCode?.text.toString().isNotEmpty()){
                val intent = Intent(activity, LobbyActivity::class.java)

                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "Masukkan Kode Permainan!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun selectedImage(position: Int) {
        for (i in 0 until list.size){
            if (i == position){
                dot[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            }
            else{
                dot[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }
        }
    }

    private fun setIndicator() {

        for(i in 0 until list.size){
            dot.add(TextView(requireContext()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dot[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            }
            else {
                dot[i].text = Html.fromHtml("&#9679")
            }

            dot[i].textSize = 12f
            view?.findViewById<LinearLayout>(R.id.indikator_home)?.addView(dot[i])
        }
    }

}