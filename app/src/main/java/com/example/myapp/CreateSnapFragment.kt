package com.example.myapp

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var photoFile: File
private const val REQUEST_CODE = 42
class CreateSnapFragment : Fragment(


) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        photoFile = getPhotoFile()

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val fileProvider = FileProvider.getUriForFile(this.requireContext(), "com.example.myapp.fileprovider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_create_snap, container, false)
        val shareButton = fragmentView.findViewById<FloatingActionButton>(R.id.fab_share)
        shareButton.setOnClickListener{
            val titleInput = fragmentView.findViewById<EditText>(R.id.EditTextInputLayout).text

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$titleInput")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            val imageView = this.view?.findViewById<ImageView>(R.id.takenImageView)
            imageView?.setImageBitmap(takenImage)

        }
    }

    private fun getPhotoFile(): File {
      //  var fileName  = UUID.randomUUID().toString()
        val fileName = "photo.jpg"
        val storageDirectory = this.context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateSnapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateSnapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}