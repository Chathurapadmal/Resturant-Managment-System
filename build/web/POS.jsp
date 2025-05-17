<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>POS UI with Dropdown Categories</title>
  <style>
    * { box-sizing: border-box; }

    body {
      margin: 0;
      font-family: 'Segoe UI', sans-serif;
      background-color: #8fd2ff;
      color: #1f2937;
    }

.logo{
    
    width: auto;
    height: 6rem;
}
    .header {
max-height: 4rem;
     display: flex; align-items: center; gap: 1rem;
        background: linear-gradient(to right, rgba(33, 122, 255, 1), rgba(33, 122, 255, 0.8));              

    }

    .header button {
      background-color: #4f46e5;
      color: white;
      padding: 0.8rem 2rem;
      border: none;
      border-radius: 8px;
      font-size: 1rem;
      cursor: pointer;
    }

    .header .profile {
        display: block;
        margin-right: 2rem;
    margin-left: auto;
      font-size: 1.5rem;
      font-weight: bold;
      align-self: center;
    }




.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #1e1e2f; 
  padding: 0.8rem 1.5rem;
  color: white;
}


.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
}

.nav-link:hover {
  color: #f7a440; 
}

.profile {
  margin-left: auto;
  font-weight: bold;
  padding-left: 2rem;
}








    .container {
      display: flex;
      gap: 2rem;
      padding: 2rem;
      flex-wrap: wrap;
    }

    .menu, .order {
      flex: 1;
      background-color: #ffffff;
      padding: 1rem;
      border-radius: 12px;
      min-width: 320px;
    }

    .menu h2, .order h2 {
      text-align: center;
      margin-bottom: 1rem;
    }

    .category {
      margin-bottom: 2rem;
    }

    .category-button {
      width: 100%;
      background-color: #46b3e5;
      color: white;
      padding: 0.6rem;
      border: none;
      border-radius: 6px;
      text-align: left;
      font-size: 1rem;
      cursor: pointer;
      margin-bottom: 0.5rem;
    }

    .items-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(60px, 1fr));
      gap: 0.8rem;
      padding-top: 0.5rem;
    }

    .item-box {
      height: 60px;
      background-color: #e0e7ff;
      border-radius: 8px;
    }

    .input-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin: 0.5rem 0;
    }

    .input-row span {
      flex: 1;
    }

    .input-row input {
      flex: 2;
      padding: 0.5rem;
      border: 1px solid #cbd5e1;
      border-radius: 6px;
      background-color: #f1f5f9;
    }

    .order-list {
      margin: 1rem 0;
    }

    .order-item {
      display: flex;
      justify-content: space-between;
      padding: 0.4rem 0;
      border-bottom: 1px solid #e5e7eb;
    }

    .order-summary {
      margin-top: 1rem;
    }

    .order-summary .input-row input {
      width: 100%;
    }

    .order button {
      margin-top: 1.5rem;
      width: 100%;
      padding: 0.8rem;
      background-color: #46b3e5;
      color: white;
      font-size: 1rem;
      border: none;
      border-radius: 8px;
      cursor: pointer;
    }

    @media (max-width: 768px) {
      .container {
        flex-direction: column;
      }
    }
  </style>
</head>
<body>

  <div class="header">
    <div >
        <img src="icons/logo.png" class="logo"    alt="">
    </div>
    
    <div class="profile"><img src="icons/account.png" height="30px"></div>
  </div>


<header class="navbar">


  <nav class="nav-links">
    <a href="#" class="nav-link">Kitchen</a>
    <a href="#" class="nav-link">Order</a>
  </nav>

</header>





  <div class="container">
    <div class="menu">

      <h2>MENU</h2>

      <!-- Category 01 -->
      <div class="category">
        <button class="category-button" onclick="toggleCategory('cat1')">Category 01</button>
        <div class="items-grid" id="cat1" style="display: none;">
          <div class="item-box"></div><div class="item-box"></div><div class="item-box"></div>
          <div class="item-box"></div><div class="item-box"></div><div class="item-box"></div>
        </div>
      </div>

      <!-- Category 02 -->
      <div class="category">
        <button class="category-button" onclick="toggleCategory('cat2')">Category 02</button>
        <div class="items-grid" id="cat2" style="display: none;">
          <div class="item-box"></div><div class="item-box"></div><div class="item-box"></div>
          <div class="item-box"></div><div class="item-box"></div><div class="item-box"></div>
        </div>
      </div>
    </div>

    <div class="order">
      <h2>Order</h2>

      <div class="input-row">
        <span>Customer</span>
        <input type="text" />
      </div>

      <div class="input-row">
        <span>Table</span>
        <input type="text" />
      </div>

      <div class="order-list">
        <div class="order-item">
          <span>Order Item 01</span>
          <span>2</span>
          <span>500</span>
        </div>
        <div class="order-item">
          <span>Order Item 01</span>
          <span>2</span>
          <span>500</span>
        </div>
      </div>

      <div class="order-summary">
        <div class="input-row">
          <span>Total</span>
          <input type="text" />
        </div>
        <div class="input-row">
          <span>Cash</span>
          <input type="text" />
        </div>
        <div class="input-row">
          <span>Balance</span>
          <input type="text" />
        </div>
      </div>

      <button>Add TO Bill</button>
    </div>
  </div>

  <script>
    function toggleCategory(id) {
      const grid = document.getElementById(id);
      grid.style.display = grid.style.display === "none" ? "grid" : "none";
    }
  </script>

</body>
</html>
