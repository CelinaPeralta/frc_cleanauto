#include <Adafruit_NeoPixel.h>
#include "ESP8266WiFi.h"
#include "ESP8266HTTPClient.h"
#include <SimpleTimer.h>

#define PIXEL_PIN D3
#define PIXEL_PIN_2 D4
#define PIXEL_COUNT 256

int width = 16;
int height = 16;

SimpleTimer timer;
int nums[10][17][2] = {{{5,1},{5,2},{6,0},{6,3},{7,0},{7,3},{8,0},{8,3},{9,0},{9,3},{10,1},{10,2}},{{5,1},{6,0},{6,1},{7,1},{8,1},{9,1},{10,0},{10,1},{10,2}},{{5,0},{5,1},{5,2},{5,3},{6,3},{7,3},{8,2},{9,1},{10,0},{10,1},{10,2},{10,3}},{{5,0},{5,1},{5,2},{5,3},{6,3},{7,1},{7,2},{8,3},{9,3},{10,0},{10,1},{10,2}},{{5,1},{5,2},{6,1},{6,2},{7,0},{7,2},{8,0},{8,2},{9,0},{9,1},{9,2},{9,3},{10,2}},{{5,0},{5,1},{5,2},{5,3},{6,0},{7,0},{7,1},{7,2},{8,3},{9,3},{10,0},{10,1},{10,2}},{{5,1},{5,2},{5,3},{6,0},{7,0},{7,1},{7,2},{7,3},{8,0},{8,3},{9,0},{9,3},{10,1},{10,2}},{{5,0},{5,1},{5,2},{5,3},{6,3},{7,2},{8,2},{9,1},{10,1}},{{5,0},{5,1},{5,2},{5,3},{6,0},{6,3},{7,1},{7,2},{8,0},{8,2},{8,3},{9,0},{9,3},{10,0},{10,1},{10,2},{10,3}},{{5,1},{5,2},{6,0},{6,3},{7,0},{7,3},{8,0},{8,1},{8,2},{8,3},{9,3},{10,0},{10,1},{10,2}}};
Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel strip2 = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN_2, NEO_GRB + NEO_KHZ800);

int sad[4][20][2] = {{{5, 9}, {5, 10}, {5, 14}, {5, 15}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {10, 9}, {10, 10}, {10, 11}, {10, 12}, {10, 13}, {10, 14}, {10, 15}, {11, 8}, {11, 16}, {12, 7}, {12, 17}, { -1, -1}}, {{5, 9}, {5, 10}, {5, 14}, {5, 15}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {7, 16}, {10, 9}, {10, 10}, {10, 11}, {10, 12}, {10, 13}, {10, 14}, {10, 15}, {11, 8}, {11, 16}, {12, 7}, {12, 17}}, {{5, 9}, {5, 10}, {5, 14}, {5, 15}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {8, 17}, {10, 9}, {10, 10}, {10, 11}, {10, 12}, {10, 13}, {10, 14}, {10, 15}, {11, 8}, {11, 16}, {12, 7}, {12, 17}}, {{5, 9}, {5, 10}, {5, 14}, {5, 15}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {9, 18}, {10, 9}, {10, 10}, {10, 11}, {10, 12}, {10, 13}, {10, 14}, {10, 15}, {11, 8}, {11, 16}, {12, 7}, {12, 17}}};
int happy[2][19][2] = {{{5, 9}, {5, 10}, {5, 14}, {5, 15}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {11, 7}, {11, 17}, {12, 8}, {12, 16}, {13, 9}, {13, 10}, {13, 11}, {13, 12}, {13, 13}, {13, 14}, {13, 15}}, {{6, 8}, {6, 9}, {6, 10}, {6, 14}, {6, 15}, {6, 16}, {11, 7}, {11, 17}, {12, 8}, {12, 16}, {13, 9}, {13, 10}, {13, 11}, {13, 12}, {13, 13}, {13, 14}, {13, 15}, { -1, -1}, { -1, -1}}};

void set(int i, int j, int r, int g, int b) {  
  if (i < 0 || j < 0 || i >= height || j >= width) return;

  if (!(i & 1))
    j = width - 1 - j;
  i = height - 1 - i;

  strip.setPixelColor(width * i + j, r, g, b);
}

void set2(int i, int j, int r, int g, int b) {  
  j -= 4;
  i -= 2;
  if (i < 0 || j < 0 || i >= height || j >= width) return;

  if (i & 1)
    j = width - 1 - j;
  i = height - 1 - i;

  strip2.setPixelColor(width * i + j, r, g, b);
}

void set(int i, int j, int x) {
  if (i < 0 || j < 0 || i >= height || j >= width) return;

  if (!(i & 1))
    j = width - 1 - j;
  i = height - 1 - i;

  strip.setPixelColor(width * i + j, x);
}

int ourTeam = 0, beampos = 0;
int newstate = 0; 

void getData(){
  for (int i = 0; i < PIXEL_COUNT; i++)
    if (i % width !=0 && i % width != width - 1 && i/width != 0 && i/width != height - 1)
      strip.setPixelColor(i, 0, 0, 0);
    
  HTTPClient http;
  http.begin("http://iot.ed.ward.li/lastmatch.txt");
  int httpCode = http.GET();
  if (httpCode == HTTP_CODE_OK) {
    WiFiClient * stream = http.getStreamPtr();
    int winningTeam = stream->parseInt();
    int weWon = stream->parseInt();
    int scores[2] = {stream->parseInt(),stream->parseInt()};

    Serial.println(winningTeam);
    Serial.println(weWon);
    Serial.println(scores[0]);
    Serial.println(scores[1]);

    newstate = weWon;
    
    disp(0, scores[ourTeam], ourTeam);
    disp(1, scores[1-ourTeam], 1-ourTeam);

    ourTeam = winningTeam == weWon;
  }
  strip.show();
}

void setup() {
  strip.begin();
  strip2.begin();
  
  Serial.begin(9600);

  Serial.println("Connecting...");
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);
  WiFi.begin("Edward's iPhone", "~REDACTED~");
  while (WiFi.status() !=WL_CONNECTED) {
    delay(500);
  }
  Serial.println("Connected!");

  timer.setInterval(1000*60, getData);
  getData();
}

int num_widths[10] = {4,3,4,4,4,4,4,4,4,4};
int num_px[10] = {12, 9, 12, 12, 13, 13, 14, 9, 17, 14};
int rb[2][3] = {{255,0,0},{0,0,255}};


void disp(int offset, int score, int color){
  
  int pwidth = 0;
  if (score > 9) pwidth++;
  if (score > 99) pwidth++;

  int digits[4] = {0,0,0,0};
  
  int stemp = score, pwt = pwidth;
  for (int i=0;i<pwt+1;i++){
    int x = stemp%10;
    digits[i] = x;
    stemp /= 10;
    pwidth += num_widths[x];
  }
  
  Serial.print(score);
  Serial.print(" : ");
  Serial.println(pwidth);

  int lpad = (width - pwidth)/2;

  for (int i=pwt;i>=0;i--){
    int x = digits[i];

    for (int i=0;i<num_px[x];i++){
      set(nums[x][i][0] + 8*offset - 4,lpad + nums[x][i][1], rb[color][0], rb[color][1], rb[color][2]);
    }
    lpad += 1 + num_widths[x];
  }
}

int state = -1, frame = 0;

void loop() {
  int tmppos = getCoord(beampos);
  set(tmppos/1000, tmppos%1000, 0,0,0);
  
  beampos = (beampos+1)%(2*width + 2*height - 4);
  
  for (int bp = beampos; bp < beampos +10;bp++){
    int bpg = bp%(2*width + 2*height - 4);
    int pos_c = getCoord(bpg);
    set(pos_c/1000, pos_c%1000, rb[ourTeam][0], rb[ourTeam][1], rb[ourTeam][2]);
  }

  if (state == newstate)
    frame++;
  else
    frame = 0;

  state = newstate;
  int rf;
  
  for (int i = 0; i < PIXEL_COUNT; i++)
    strip2.setPixelColor(i, 0, 0, 0);

  switch(state) {
  case 1:
    rf = (frame % 40) > 31;
    Serial.print("rf is ");
    Serial.println(rf);
    for (int i = 0; i < sizeof(happy[rf]) / sizeof(happy[rf][0]); i++)
      set2(happy[rf][i][0], happy[rf][i][1], 165, 165, 165);
    break;
  case 0:
    rf = max((frame % 40) / 5 - 4, 0);
    for (int i = 0; i < sizeof(sad[rf]) / sizeof(sad[rf][0]); i++)
      set2(sad[rf][i][0], sad[rf][i][1], 165, 165, 165);
    break;
  }
  
  
  strip.show();
  strip2.show();
  timer.run();
  delay(30);
}

int getCoord(int pos){
  if (0<= pos && pos < width){
    return 0*1000 + (width - pos - 1);
  }else if (width <= pos && pos <width+height-1){
    return (pos - width + 1)*1000 + 0;
  }else if (width+height-1 <= pos && pos < width*2 + height - 2){
    return (height-1)*1000 + (pos - width - height + 2);
  }else
    return (height - (pos - width - height- width) - 4)*1000 + (width-1);
}

