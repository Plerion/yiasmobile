attribute vec2 position0;
attribute vec4 color0;
attribute vec2 texcoord0;

uniform mat4 matProj;
uniform mat4 matUI;

varying vec4 outColor;
varying vec2 texCoord;

void main() {
	texCoord = texcoord0;
	outColor = color0;
	gl_Position = matProj * matUI * vec4(position0, 0.0, 1.0);
}