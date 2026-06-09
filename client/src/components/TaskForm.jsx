import { useState } from "react";
import axios from "../api/axios";

export default function TaskForm({ onTaskAdded }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("PENDING");
  const [priority, setPriority] = useState("MEDIUM");
  const [dueDate, setDueDate] = useState("");
  const [file, setFile] = useState(null);
 
  // const addTask = async () => {
  //   await axios.post("/api/tasks", {
  //     title,
  //     description,
  //     status,
  //     priority,
  //     dueDate,
  //     userId: localStorage.getItem("userId"),
  //   });

    // setTitle("");
    // setDescription("");
    // setStatus("PENDING");
    // setPriority("MEDIUM");
    // // setDueDate("");

  //   if (onTaskAdded) {
  //     await onTaskAdded();
  //   }
  // };
  
  const addTask = async () => {
    console.log("BEFORE POST");

    const res = await axios.post("/api/v1/tasks", {
      title,
      description,
      status,
      priority,
      dueDate,
      userId: Number(localStorage.getItem("userId")),
    });

    console.log("USER ID:", localStorage.getItem("userId"));
    //upload file
    if(file){
      const formData = new FormData();

      formData.append("file",file);

      await axios.post(`api/v1/tasks/${res.data.id}/upload`, formData,
        {
          headers: {
            "Content-Type":
            "multipart/form-data",
          },
        }
      );
    }

    if (onTaskAdded) {
      await onTaskAdded();
    }
  };

   return (
     <div className="bg-white p-6 rounded-xl shadow-md mb-8">
       <h2 className="text-2xl font-semibold mb-4">Add New Task</h2>

       <div className="flex flex-col gap-4">
         <input
           type="text"
           placeholder="Enter task title"
           value={title}
           onChange={(e) => setTitle(e.target.value)}
           className="border p-3 rounded-lg outline-none focus:ring-2 focus:ring-blue-400"
         />

         <textarea
           placeholder="Enter description"
           value={description}
           onChange={(e) => setDescription(e.target.value)}
           className="border p-3 rounded-lg outline-none focus:ring-2 focus:ring-blue-400"
         />

         <select
           value={status}
           onChange={(e) => setStatus(e.target.value)}
           className="border p-3 rounded-lg"
         >
           <option value="PENDING">PENDING</option>
           <option value="IN_PROGRESS">IN-PROGRESS</option>
           <option value="COMPLETED">COMPLETED</option>
         </select>

         <select
           value={priority}
           onChange={(e) => setPriority(e.target.value)}
           className="border p-3 rounded-lg"
         >
           <option value="HIGH">HIGH</option>
           <option value="MEDIUM">MEDIUM</option>
           <option value="LOW">LOW</option>
         </select>

         <input
           type="date"
           value={dueDate}
           onChange={(e) => setDueDate(e.target.value)}
         />

         <input
   type="file"
   onChange={(e) => setFile(e.target.files[0])}
/>

         <button
           onClick={addTask}
           className="bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition"
         >
           Add Task
         </button>
       </div>
     </div>
   );
}