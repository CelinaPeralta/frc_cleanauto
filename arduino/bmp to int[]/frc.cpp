#include <bits/stdc++.h>
#include "bitmap_image.hpp"

using namespace std;

int main() {
  vector<string> names;
  for (char c ='a';c<='z';c++){
    names.push_back(string(1,c) + ".bmp");
  }

  freopen("out.txt", "w", stdout);

  for (int fn = 0; fn < names.size(); fn++) {

    bitmap_image image(names[fn]);

    if (!image) {
      printf("Error - Failed to open '%s'\n", names[fn].c_str());
      return 1;
    }

    rgb_t color;
    int c = 0;
    for (int i = 0; i < image.height(); i++)
      for (int j = 0; j < image.width(); j++) {
        image.get_pixel(j, i, color);
        if ((color.red != 255 || color.green != 255 || color.blue != 255) && ++c) {

        }

      }

    bool first = true;
    cout << "int " << names[fn].substr(0, names[fn].size() - 4) << "[" << c << "][5] = {";
    for (int i = 0; i < image.height(); i++)
      for (int j = 0; j < image.width(); j++) {
        image.get_pixel(j, i, color);
        if ((color.red != 255 || color.green != 255 || color.blue != 255)){
          if (!first)
            cout << ",";
          first = false;
          cout << "{" << i << "," << j << "," << (int)color.red << "," << (int)color.green << "," << (int)color.blue << "}";
        }
      }
    cout << "};\n";
  }
  return 0;
}

