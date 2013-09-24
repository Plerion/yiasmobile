attribute vec2 position0;
attribute vec4 color0;

uniform mat4 matProj;

varying vec4 outColor;

void main() {
	outColor = color0;
	gl_Position = matProj * vec4(position0, 0.0, 1.0);
}