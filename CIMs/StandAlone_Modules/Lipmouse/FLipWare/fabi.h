#include <Arduino.h>
#include <string.h>
#include <stdint.h>


// #define ARDUINO_PRO_MICRO   //  if Arduino Leonardo or Arduino Pro Micro is used  (comment or remove if Teensy is used !)
#define TEENSY                 //  if teensy is used (but not a lipmouse module)
// #define LIPMOUSE_V0         //  first HW version of lipmouse, powers pressure sensor via GPIO pins !
 

#define NUMBER_OF_BUTTONS 11          // number of connected or virtual switches
#define NUMBER_OF_PHYSICAL_BUTTONS 3  // number of connected switches
#define MAX_SLOTS         10          // maximum number of EEPROM memory slots

#define MAX_KEYSTRING_LEN 50          // maximum lenght for key identifiers / keyboard text
#define MAX_CMDLEN MAX_KEYSTRING_LEN+3

#define DEBUG_NOOUTPUT 0
#define DEBUG_FULLOUTPUT 1
#define DEBUG_LIVEREPORTS 2
#define DEFAULT_DEBUGLEVEL DEBUG_FULLOUTPUT

// command identifiers
#define CMD_PRINT_ID                 1
#define CMD_BUTTON_MODE              2
#define CMD_MOUSE_CLICK_LEFT         3
#define CMD_MOUSE_CLICK_RIGHT        4
#define CMD_MOUSE_CLICK_DOUBLE       5
#define CMD_MOUSE_CLICK_MIDDLE       6
#define CMD_MOUSE_PRESS_LEFT         7
#define CMD_MOUSE_PRESS_RIGHT        8
#define CMD_MOUSE_PRESS_MIDDLE       9
#define CMD_MOUSE_RELEASE_LEFT      10
#define CMD_MOUSE_RELEASE_RIGHT     11
#define CMD_MOUSE_RELEASE_MIDDLE    12
#define CMD_MOUSE_WHEEL_UP          13
#define CMD_MOUSE_WHEEL_DOWN        14
#define CMD_MOUSE_WHEEL_STEP        15
#define CMD_MOUSE_MOVEX             16
#define CMD_MOUSE_MOVEY             17
#define CMD_KEY_WRITE               18
#define CMD_KEY_PRESS               19
#define CMD_KEY_RELEASE             20
#define CMD_SAVE_SLOT               21
#define CMD_LOAD_SLOT               22
#define CMD_LIST_SLOTS              23
#define CMD_NEXT_SLOT               24
#define CMD_DELETE_SLOTS            25

#define CMD_LM_ON                   50
#define CMD_LM_OFF                  51
#define CMD_LM_CA                   52
#define CMD_LM_AX                   53
#define CMD_LM_AY                   54
#define CMD_LM_DX                   55
#define CMD_LM_DY                   56
#define CMD_LM_TS                   57
#define CMD_LM_TP                   58
#define CMD_LM_SR                   59
#define CMD_LM_ER                   60
#define CMD_LM_TT                   61

#define CMD_IDLE                    100

// Global Variables
#include <stdint.h>

struct settingsType {
  int8_t  input_map[NUMBER_OF_PHYSICAL_BUTTONS];
  uint8_t LED_PIN;
  uint8_t  mouseOn;
  uint8_t  ws;     // wheel stepsize  
  uint8_t  ax;     // acceleration x
  uint8_t  ay;     // acceleration y
  uint8_t  dx;     // deadzone x
  uint8_t  dy;     // deadzone y
  uint16_t ts;     // threshold sip
  uint16_t tp;     // threshold puff 
  uint16_t tt;     // threshold time 
};

struct buttonType {                         // holds command and data for a button function 
  int mode;
  int value;
  char keystring[MAX_KEYSTRING_LEN];
  uint8_t bounceCount;
  uint8_t bounceState;
  uint8_t stableState;
  uint8_t longPressed;
  uint32_t timestamp;
} ; 

extern uint8_t DebugOutput;

extern struct settingsType settings;
extern int EmptySlotAddress;
extern struct buttonType buttons[NUMBER_OF_BUTTONS];

void performCommand (uint8_t cmd, int16_t par1, char * keystring, int8_t periodicMouseMovement);
void saveToEEPROM(char * slotname);
void readFromEEPROM(char * slotname);
void deleteSlots();
void listSlots();


void BlinkLed();
int freeRam ();
void parseByte (int newByte);

void releaseKeys();  // releases all previously pressed keys
void setKeyValues(char* text);



