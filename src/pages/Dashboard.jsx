import React, { useEffect, useState } from "react";
import axios from "../api/axios";
import { FiSearch, FiFilter, FiArrowUp, FiArrowDown } from "react-icons/fi";
import TaskForm from "../components/TaskForm";
import TaskList from "../components/TaskList";

export default function Dashboard() {
  const [tasks, setTasks] = useState([]);
  const [editingTask, setEditingTask] = useState(null);
  const [userId, setUserId] = useState(null);
  const [page, setPage] = useState(0);
  // const size = 5;
  const [searchQuery, setSearchQuery] = useState("");
  const [statusFilter, setStatusFilter] = useState("");
  const [priorityFilter, setPriorityFilter] = useState("");
  const [sortBy, setSortBy] = useState("");
  const [sortDir, setSortDir] = useState("");
  const [notifications, setNotifications] = useState([]);
  const [refresh, setRefresh] = useState(false);

  // get userId once
  useEffect(() => {
    const id = localStorage.getItem("userId");
    setUserId(id);
  }, []);

  // fetch tasks
  const getTasks = async () => {
    if (!userId || userId === "undefined") return;

    try {
      let url = `http://localhost:8080/api/v1/tasks?page=${page}&size=5`;

      // SEARCH
      if (searchQuery.trim()) {
        url = `http://localhost:8080/api/v1/tasks/search?query=${searchQuery}&page=${page}&size=5`;
      }

      //FILTER
      if (statusFilter || priorityFilter) {
        url = `http://localhost:8080/api/v1/tasks/filter?page=${page}&size=5`;

        if (statusFilter) {
          url += `&status=${statusFilter}`;
        }

        if (priorityFilter) {
          url += `&priority=${priorityFilter}`;
        }
      }

      //SORTING
      if (sortDir) {
        url += `&sort=dueDate,${sortDir}`;
      }

      const res = await axios.get(url);
      console.log(url);
      setTasks(res.data.content);
    } catch (err) {
      console.log("Error fetching tasks:", err);
    }
  };

  //partial update task
  const updateStatus = async (taskId, status) => {
    try {
      await axios.patch(`/api/v1/tasks/${taskId}`, {
        status: status,
      });

      getTasks();
    } catch (err) {
      console.log("Update error:", err);
    }
  };

  //delete task
  const deleteTask = async (taskId) => {
    try {
      await axios.delete(`api/v1/tasks/${taskId}`);
      getTasks();
    } catch (err) {
      console.log("Delete error: ", err);
    }
  };

  //notifcations
  const getNotifications = async () => {
    try {
      if (!userId) return;
      const res = await axios.get(`api/notifications/${userId}`);
      console.log("notifications API response:", res.data);
      setNotifications(Array.isArray(res.data) ? res.data : []);
      setNotifications(res.data);
      setRefresh((prev) => !prev);
    } catch (err) {
      console.log(err);
      setNotifications([]);
    }
  };

  useEffect(() => {
    getTasks();
  }, [
    page,
    searchQuery,
    statusFilter,
    priorityFilter,
    sortBy,
    sortDir,
    refresh,
  ]);

  useEffect(() => {
    if (userId) {
      getNotifications();
    }
  }, [userId]);

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-4xl font-bold text-center mb-8 text-blue-600">
          Task Manager
        </h1>
        <TaskForm onTaskAdded={getTasks} />

        {/* Search */}
        <div className="relative flex-1">
          <FiSearch className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-4 h-4" />
          <input
            type="text"
            placeholder="Search tasks..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-9 pr-4 py-2 rounded-xl border border-gray-200 bg-gray-50 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition"
          />
        </div>

        {/* Divider */}
        <div className="h-5 w-px bg-gray-200 shrink-0" />

        {/* Filter icon */}
        <FiFilter className="text-gray-400 w-4 h-4 shrink-0" />

        {/* Status */}
        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="shrink-0 px-3 py-2 rounded-xl border border-gray-200 bg-gray-50 text-sm text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer transition"
        >
          <option value="">All Status</option>
          <option value="PENDING">Pending</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="COMPLETED">Completed</option>
        </select>

        {/* Priority */}
        <select
          value={priorityFilter}
          onChange={(e) => setPriorityFilter(e.target.value)}
          className="shrink-0 px-3 py-2 rounded-xl border border-gray-200 bg-gray-50 text-sm text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer transition"
        >
          <option value="">All Priority</option>
          <option value="LOW">Low</option>
          <option value="MEDIUM">Medium</option>
          <option value="HIGH">High</option>
        </select>

        {/* Divider */}
        <div className="h-5 w-px bg-gray-200 shrink-0" />

        {/* Sort icon - changes based on selected value */}
        {sortDir === "asc" ? (
          <FiArrowUp className="text-blue-500 w-4 h-4 shrink-0" />
        ) : sortDir === "desc" ? (
          <FiArrowDown className="text-blue-500 w-4 h-4 shrink-0" />
        ) : (
          <FiArrowUp className="text-gray-400 w-4 h-4 shrink-0" />
        )}

        {/* Sort */}
        <select
          value={sortDir}
          onChange={(e) => setSortDir(e.target.value)}
          className="shrink-0 px-3 py-2 rounded-xl border border-gray-200 bg-gray-50 text-sm text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer transition"
        >
          <option value="">No Sorting</option>
          <option value="asc">Due Date ↑</option>
          <option value="desc">Due Date ↓</option>
        </select>
        <div className="mb-6">
          <h2 className="text-xl font-bold mt-5">Notifications</h2>

          {notifications.length === 0 ? (
            <p>No notifications</p>
          ) : (
            notifications.map((n) => (
              <div
                key={n.id}
                className="
            bg-yellow-100
            p-3
            rounded
            mt-2"
              >
                🔔 {n.message}
              </div>
            ))
          )}
        </div>

        <TaskList
          tasks={tasks}
          onDelete={deleteTask}
          onUpdateStatus={updateStatus}
          getTasks={getTasks}
        />

        <div className="flex justify-center gap-3 mt-6">
          <button
            onClick={() => setPage((p) => Math.max(p - 1, 0))}
            disabled={page === 0}
            className="px-4 py-2 bg-gray-200 rounded disabled:opacity-50"
          >
            Prev
          </button>

          <span className="px-3 py-2">{page + 1}</span>

          <button
            onClick={() => setPage((p) => p + 1)}
            className="px-4 py-2 bg-blue-600 text-white rounded"
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}
