package com.redomar.flappu.maths;

import static java.lang.Math.*;

public class Matrix4f {

	public float[] matrix = new float[4 * 4]; // the size of the array is 16.

	/**
	 * This method creates an identity matrix
	 * which will give the result of any number
	 * multiplied by it as a matrix. It is similar
	 * to multiplying by 1<br><hr>
	 * [1][0][0][0]<br>
	 * [0][1][0][0]<br>
	 * [0][0][1][0]<br>
	 * [0][0][0][1]
	 */
	public static Matrix4f identity(){
		Matrix4f result = new Matrix4f();

		// Will fill every array element with 0.
		for (int i = 0; i < 4 * 4; i++){
			result.matrix[i] = 0.0f;
		}

		// Will fill diagonal elements with 1.
		result.matrix[0 + 0 * 4] = 1.0f;
		result.matrix[1 + 1 * 4] = 1.0f;
		result.matrix[2 + 2 * 4] = 1.0f;
		result.matrix[3 + 3 * 4] = 1.0f;

		return result;
	}

	public Matrix4f multiply(Matrix4f matrix){
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < 4 ; y++){ // For each column
			for (int x = 0; x < 4; x++){ // For each row
				float sum = 0.0f; // Declaring sum.
				for (int e = 0; e < 4; e++){
					// Multiplying the elements together and Adding to the overall sum.
					//          Column            *        Row.
					sum += this.matrix[e + y * 4] * matrix.matrix[x + e * 4];
				}
				result.matrix[x + y * 4] = sum;
			}
		}
		return result;
	}

	/**
	 * [1][0][0][x]<br>
	 * [0][1][0][y]<br>
	 * [0][0][1][z]<br>
	 * [0][0][0][1]
	 */
	public static Matrix4f translation(Vector3f vector){
		Matrix4f result = new Matrix4f();

		result.matrix[0 + 3 * 4] = vector.x;
		result.matrix[1 + 3 * 4] = vector.y;
		result.matrix[2 + 3 * 4] = vector.z;

		return result;
	}

	/**
	 * Rotates objects on the z axes<br><hr>
	 *
	 * [cos(x)][-sin(x)][0][0]<br>
	 * [sin(x)][cos(x)][0][0]<br>
	 * [0][0][1][0]<br>
	 * [0][0][0][1]
	 */
	public static Matrix4f rotation(float angle){
		Matrix4f result = identity();

		float r = (float) toRadians(angle);
		float cos = (float) cos(r);
		float sin = (float) sin(r);

		result.matrix[0 + 0 * 4] = cos;
		result.matrix[1 + 0 * 4] = sin;

		result.matrix[0 + 1 * 4] = -sin;
		result.matrix[1 + 1 * 4] = cos;

		return result;
	}

	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
		Matrix4f result = identity();

		result.matrix[0 + 0 * 4] = 2.0f / (right - left);

		result.matrix[1 + 1 * 4] = 2.0f / (top - bottom);

		result.matrix[2 + 2 * 4] = 2.0f / (near - far);

		result.matrix[0 + 3 * 4] = (left + right) / (left - right);
		result.matrix[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.matrix[2 + 3 * 4] = (near + far) / (far - near);

		return result;
	}

}
