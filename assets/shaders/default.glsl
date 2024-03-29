#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;

out vec4 fColor;


void main()
{
    fColor = aColor;
    gl_Position = vec4(aPos,1.0);
}


#type fragment
#version 330 core
uniform vec2 resolution;
uniform float time;
vec2 fragCoord = gl_FragCoord.xy;
out vec4 theColor;
//in vec4 fColor;
//out vec4 color;

void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = fragCoord/resolution.xy *2.0 -1.0;

    float siri = abs(sin(time - uv.y)) / length(uv.y) * 0.05;

    vec3 col = siri *  mix(vec3(0.8,0.3,0.7),vec3(0.1,0.5,0.3),abs(uv.x));

    // Output to screen
    theColor = vec4(col,1.0);
}
