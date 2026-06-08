import React, { useState } from "react";
import { FiDownload } from "react-icons/fi";
import axios from "../api/axios";
import { MdCalendarToday, MdDelete, MdEdit } from "react-icons/md";

export default function TaskList({ tasks = [], onDelete, onUpdateStatus, getTasks }) {
  const [expanded, setExpanded] = useState({});
  const [commentText, setCommentText] = useState({});

  const toggleExpand = (id) => {
    setExpanded((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const addComment = async (taskId) => {
    try {
      await axios.post("/api/v1/tasks/comments", {
        text: commentText[taskId],
        taskId: taskId,
      });
      setCommentText((prev) => ({
        ...prev,
        [taskId]: ""
      }));
      getTasks();
    } catch (err) {
      console.error("Failed to add comment", err);
    }
  };

  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4 mt-5">Your Tasks</h2>

      <div className="grid gap-4">
        {!tasks || tasks.length === 0 ? (
          <div className="text-center text-gray-500 py-10">
            Please add tasks
          </div>
        ) : (
          tasks.map((task) => (
            <div
              key={task.id}
              className="bg-white p-5 rounded-xl shadow-md border-l-4 border-blue-500"
            >
              {/* Top row: title + actions */}
              <div className="flex justify-between items-center mb-2">
                <h3 className="text-xl font-bold">{task.title}</h3>

                <div className="flex items-center gap-2">
                  {/* Priority badge */}
                  <span
                    className={`px-2 py-0.5 rounded-md text-xs font-semibold ${
                      task.priority === "HIGH"
                        ? "bg-red-100 text-red-600"
                        : task.priority === "MEDIUM"
                          ? "bg-yellow-100 text-yellow-600"
                          : "bg-gray-100 text-gray-500"
                    }`}
                  >
                    {task.priority}
                  </span>

                  {/* Status badge */}
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-semibold ${
                      task.status === "COMPLETED"
                        ? "bg-green-100 text-green-700"
                        : task.status === "IN_PROGRESS"
                          ? "bg-blue-100 text-blue-700"
                          : task.status === "PENDING"
                            ? "bg-red-100 text-red-700"
                            : "bg-yellow-100 text-yellow-700"
                    }`}
                  >
                    {task.status}
                  </span>

                  {/* Edit button */}
                  <button
                    onClick={() =>
                      onUpdateStatus?.(
                        task.id,
                        task.status === "PENDING"
                          ? "IN_PROGRESS"
                          : task.status === "IN_PROGRESS"
                            ? "COMPLETED"
                            : "PENDING",
                      )
                    }
                    className="p-1.5 rounded-lg text-gray-400 hover:text-blue-500 hover:bg-blue-50 transition"
                    title="Update status"
                  >
                    <MdEdit size={18} />
                  </button>

                  {/* Delete button */}
                  <button
                    onClick={() => onDelete?.(task.id)}
                    className="p-1.5 rounded-lg text-gray-400 hover:text-red-500 hover:bg-red-50 transition"
                    title="Delete task"
                  >
                    <MdDelete size={18} />
                  </button>
                </div>
              </div>

              {/* Description */}
              {/* Description + Date row */}
              <div className="flex justify-between items-start">
                <div className="max-w-[75%]">
                  <p
                    className={`text-gray-600 text-sm ${expanded[task.id] ? "" : "line-clamp-2"}`}
                  >
                    {task.description}
                  </p>
                  <button
                    onClick={() => toggleExpand(task.id)}
                    className="text-xs text-blue-500 hover:underline mt-1"
                  >
                    {expanded[task.id] ? "Show less" : "Show more"}
                  </button>
                </div>
                <div className="flex items-center gap-1 text-xs text-gray-400 shrink-0 ml-4">
                  <MdCalendarToday size={13} />
                  <span>{task.dueDate}</span>
                </div>
              </div>

              {/* {task.attachment && <p>📎 {task.attachment}</p>} */}

              {task.attachment && (
                <a
                  href={`http://localhost:8080/uploads/${task.attachment}`}
                  target="_blank"
                >
                  📎 {task.attachment}
                </a>
              )}

              <input
                type="text"
                placeholder="Add comment"
                value={commentText[task.id] || ""}
                onChange={(e) =>
                  setCommentText({
                    ...commentText,
                    [task.id]: e.target.value,
                  })
                }
                className="border p-2 rounded mt-3 w-full "
              />

              <button
                onClick={() => addComment(task.id)}
                className="bg-blue-500 text-white px-3 py-1 rounded mt-2"
              >
                Add
              </button>
              <div className="mt-3">
                {task.comments?.map((comment) => (
                  <p
                    key={comment.id}
                    className="text-sm text-gray-600 border-b py-1"
                  >
                    {comment.text}
                  </p>
                ))}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
