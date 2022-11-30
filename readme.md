# striketool - cli for alesis strike drum module

### Disclaimer: This tool is NO OFFICIAL Alesis tool, it is not verified by the manufacturer and COULD CAUSE DAMAGE to your device! Using this tool COULD VOID THE WARRANTY of your device! Do NOT use this tool for copyright infringements. USE AT YOUR OWN RISK!

### The striketool was created to make the Alesis strike module accessible on Linux for troubleshooting purposes.

## Usage

```

 striketool <port> list|openrw|openro|close

 e.g. ./striketool hw:2,0,0 openrw

   list - list midi capable alesis devices
   openrw - enable internal access in 'content update' mode
   openro - enable internal access in read only mode
   close - reset strike module in standard mode

```

## License
[GPL-2.0](./license.txt)
