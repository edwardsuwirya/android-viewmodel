package com.enigmacamp.myviewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.enigmacamp.myviewmodel.databinding.FragmentHelloBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HelloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelloFragment : Fragment() {
    lateinit var binding: FragmentHelloBinding
    lateinit var viewModel: MainActivityViewModel

    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelloBinding.inflate(layoutInflater)
        val onBoardingAdapter = OnBoardingAdapter(this, 3)
        binding.apply {
            helloTextView.setText(viewModel.helloWord)
            viewPagerImageSlider.adapter = onBoardingAdapter
            viewPagerImageSlider.registerOnPageChangeCallback(onBoardingPageChangeCallback)
//            val marginPageTransformer = MarginPageTransformer(50)

            viewPagerImageSlider.setPageTransformer(object : ViewPager2.PageTransformer {
                override fun transformPage(page: View, position: Float) {
                    val MIN_SCALE = 0.85f
                    val MIN_ALPHA = 0.5f
                    page.apply {
                        if (position < -1) {
                            setAlpha(0f);
                        } else if (position <= 1) { // [ -1,1 ]
                            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                            val vertMargin = height * (1 - scaleFactor) / 2;
                            val horzMargin = width * (1 - scaleFactor) / 2;
                            if (position < 0) {
                                page.setTranslationX(horzMargin - vertMargin / 2);
                            } else {
                                page.setTranslationX(-horzMargin + vertMargin / 2);
                            }
                            page.setScaleX(scaleFactor);
                            page.setScaleY(scaleFactor);
                            page.setAlpha(
                                MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
                            )
                        } else {
                            page.setAlpha(0f);
                        }
                    }
                }
            })
        }
        return binding.root
    }

    override fun onDestroy() {
        binding.viewPagerImageSlider.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()

    }

    private fun updateCircleMarker(position: Int) {
        when (position) {
            0 -> {
                binding.onBoardingInitialCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_green_circle)
                binding.onBoardingMiddleCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
            }
            1 -> {
                binding.onBoardingInitialCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_green_circle)
                binding.onBoardingLastCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
            }
            2 -> {
                binding.onBoardingInitialCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background =
                    getDrawable(requireContext(), R.drawable.bg_green_circle)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelloFragment()
    }
}