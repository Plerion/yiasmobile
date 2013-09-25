precision mediump float;

uniform sampler2D baseSampler;

varying vec4 outColor;
varying vec2 texCoord;

void main() {
	gl_FragColor = texture2D(baseSampler, texCoord);
}