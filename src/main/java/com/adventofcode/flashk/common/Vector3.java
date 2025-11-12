package com.adventofcode.flashk.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Vector3 {

	private int x;
	private int y;
	private int z;
	
	/// Accepts coordinates in String format as "x,y,z" and creates a Vector3 from it.
    /// ```
    /// Vector3 vector1 = new Vector2("4,2,1");
    /// Vector3 vector2 = new Vector2(4,2,1);
    /// 
    /// vector.equals(vector2) // true
    /// ```
    /// @param coordinates the coordinates of the vector
	public Vector3(String coordinates) {
		
		String[] values = coordinates.split(",");
		
		this.x = Integer.parseInt(values[0]);
		this.y = Integer.parseInt(values[1]);
		this.z = Integer.parseInt(values[2]);
		
	}
	
	public Vector3(Vector3 pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
	}
	
	public void transform(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
	
	public static Vector3 transform(Vector3 start, Vector3 end) {
		int x = start.x + end.x;
		int y = start.y + end.y;
		int z = start.z + end.z;
		
		return new Vector3(x,y,z);
	}
	
	/// Shorthand for `Vector3(-1,0,0)`.
    /// @return A unitary vector that points to the left.
	public static Vector3 left() {
		return new Vector3(-1,0,0);
	}
	
	/// Shorthand for `Vector3(1,0,0)`.
    /// @return A unitary vector that points to the right.
	public static Vector3 right() {
		return new Vector3(1,0,0);
	}
	
	/// Shorthand for `Vector3(0,1,0)`.
    /// @return A unitary vector that points up.
	public static Vector3 up() {
		return new Vector3(0,1,0);
	}
	
	/// Shorthand for `Vector3(0,-1,0)`.
    /// @return A unitary vector that points down.
	public static Vector3 down() {
		return new Vector3(0,-1,0);
	}

	/// Shorthand for `Vector3(0,0,1)`.
    /// @return A unitary vector that points forward.
	public static Vector3 forward() {
		return new Vector3(0,0,1);
	}

	/// Shorthand for `Vector3(0,0,-1)`.
    /// @return A unitary vector that points backwards;
	public static Vector3 backward() {
		return new Vector3(0,0,-1);
	}
	
}
