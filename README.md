# 🧪 Cellular Fluid Simulation

A **Java-based real-time fluid dynamics simulator** inspired by *“Real-Time Fluid Dynamics for Games”* by **Jos Stam (2003)**.  
This project implements a **cellular approximation of the Navier–Stokes equations**, enabling stable and visually coherent simulation of incompressible fluids through a discretized grid.  

Developed by **Neev Datta**, this simulation models how density and velocity fields evolve over time under the influence of advection, diffusion, and external forces, using numerical relaxation methods like **Gauss–Seidel iteration**.

---

## 🎯 Objective

To simulate **stable 2D incompressible fluid flow** using a **cellular grid** and the **semi-Lagrangian advection** and **projection** techniques introduced by Jos Stam. The project focuses on numerical stability and computational efficiency over high physical accuracy, making it suitable for real-time applications in visualization and game environments.

---

## 🧩 Architecture Overview

| File | Description |
|------|--------------|
| `Fluid.java` | Core simulation logic — handles advection, diffusion, projection, and velocity updates |
| `Matrix.java` | Lightweight linear algebra helper for numerical operations |
| `GaussSidel.java` | Iterative solver implementing Gauss–Seidel relaxation for pressure and diffusion systems |
| `Vector2D.java` | 2D vector arithmetic utilities |
| `Attribute.java` | Defines properties for each cell (density, velocity components, etc.) |
| `Main.java` | Entry point and environment setup for running the simulation |

---

## 🔬 Physical Model

The simulation approximates the **Navier–Stokes equations for incompressible flow**:

$$
\frac{\partial \mathbf{u}}{\partial t} + (\mathbf{u} \cdot \nabla)\mathbf{u} = -\nabla p + \nu \nabla^2 \mathbf{u} + \mathbf{F}
$$

$$
\nabla \cdot \mathbf{u} = 0
$$

Where:  
- **u** = velocity field  
- **p** = pressure field  
- **ν** = viscosity coefficient  
- **F** = external forces (e.g., user input, boundary conditions)

These equations are discretized over a **2D grid of cells**, each representing a small volume of fluid.

---

## ⚙️ Simulation Process (in `Fluid.java`)

Each frame of the simulation executes the following sequence:

1. **Add Sources**  
   Apply external inputs (forces, density injections, etc.) to the grid.

2. **Diffuse**  
   Solve diffusion using **Gauss–Seidel relaxation**, iteratively updating each cell:
   $$
   x_{i,j}^{(k+1)} = \frac{x_{i,j}^{(0)} + a(x_{i-1,j}^{(k)} + x_{i+1,j}^{(k)} + x_{i,j-1}^{(k)} + x_{i,j+1}^{(k)})}{1 + 4a}
   $$
   where \( a = \text{diffusion\_rate} \times \Delta t \times N^2 \).

3. **Advect**  
   Use **semi-Lagrangian backtracing**: for each cell, trace backward along the velocity field to find where the fluid originated from, and interpolate from neighboring cells.

4. **Project (Enforce Incompressibility)**  
   Solve for pressure `p` that makes the velocity field divergence-free:
   $$
   \nabla^2 p = \nabla \cdot \mathbf{u}
   $$
   Then subtract pressure gradients from velocity to remove divergence.

5. **Update Density Field**  
   Advect and diffuse the density field similarly, yielding realistic flow visuals.

---

## ⚡ Numerical Methods

- **Gauss–Seidel Iteration:** Used for solving linear systems in both the diffusion and projection steps.  
- **Semi-Lagrangian Advection:** Ensures unconditional stability even for large timesteps.  
- **Boundary Conditions:** Velocity set to zero at boundaries to simulate solid walls.  

This combination follows Stam’s *Stable Fluids* algorithm, prioritizing stability and simplicity for real-time performance.

---

## 🧠 Key Concepts Implemented

- **Stable Fluids algorithm (Jos Stam, 2003)**  
- **Divergence-free velocity enforcement** (projection step)  
- **Semi-Lagrangian advection** for stability  
- **Iterative Gauss–Seidel solver** for Laplace systems  
- **Discrete 2D velocity and density grids**  
- **Object-oriented modular design (Java)**  

---

## 🖥️ Running the Simulation

### Prerequisites
- Java 17 or newer  
- IDE (IntelliJ, Eclipse) or CLI environment  

### Steps
```bash
# Clone repository
git clone https://github.com/NeevtheGreat875/cellular-fluid-sim.git
cd cellular-fluid-sim

# Compile and run
javac Main.java
java Main
```

To tweak parameters such as grid resolution, viscosity, or timestep, edit constants defined in `Fluid.java`.

---

## 📊 Visualization

While the current version focuses on numerical computation, visualization can be added using:
- JavaFX / Swing to render density fields as color maps  
- CSV export + Python matplotlib for velocity field plotting  
- GIFs or PNG frames generated per timestep  

*(Add visuals or GIFs once integrated.)*

---

## 🔮 Future Work

- Implement **GPU parallelization (OpenCL/JOCL)** for real-time rendering  
- Extend to **3D grid simulation**  
- Add **temperature & buoyancy fields** (for smoke/airflow simulation)  
- Introduce **adaptive timestep control**  
- Integrate **particle advection** to visualize flow lines  

---

## 🧾 Reference

**Jos Stam**, *“Real-Time Fluid Dynamics for Games”*, SIGGRAPH 2003.  
[https://www.dgp.toronto.edu/people/stam/reality/Research/pdf/GDC03.pdf](https://www.dgp.toronto.edu/people/stam/reality/Research/pdf/GDC03.pdf)

---

## 👨‍💻 Author

**Neev Datta**  
- Formula 4 Racer | Engineering Researcher | Robotics & Simulation Enthusiast  
- Focused on Mechatronics, Computational Physics, and Intelligent Systems  
- [GitHub](https://github.com/NeevtheGreat875) | [LinkedIn](https://linkedin.com/in/neevdatta)
