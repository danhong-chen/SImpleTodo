package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {

                //1, remove the item from the list
                listOfTask.removeAt(position)
                //2. notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        //1. lets detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            Log.i("viv","User clicked")
//        }
        loadItems()
        //lookup recyclerview in layout
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViews)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTask, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //setup the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //get a reference to the button
        //and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. grab the text the user has inputted into @id/addTaskField
            var userInputtedTask = inputTextField.text.toString()
            //2. add the string to our list of tasks: listOfTasks
            listOfTask.add(userInputtedTask)
            //notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTask.size - 1)
            //3. Reset textfield
            inputTextField.setText("")

            saveItems()
        }
    }

    //save the data that the user has inputted

    //save the data by writing and reading from a file

    //get the file we need
    fun getDataFile(): File {
        //every line is going to represent a specific task
        return File(filesDir, "data.txt")
    }

    //create a method to get the file we need

    //load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTask = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTask)
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}