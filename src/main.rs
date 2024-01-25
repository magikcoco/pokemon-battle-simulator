use iced::{widget::text, Sandbox, Settings};

fn main() -> iced::Result{
    Editor::run(Settings::default())
}

struct Editor;

#[derive(Debug)]
enum Message {}

impl Sandbox for Editor {
    type Message = Message;

    fn new() -> Self {
        //the state to be in when the porgram starts
        Self
    }

    fn title(&self) -> String {
        //the title of the thing
        String::from("Pokemon Battle Simulator")
    }

    fn update(&mut self, message: Self::Message) {
        //handling messages (events that could change the state of the application)
        //change or update the application in response
        match message {}
    }

    fn view(&self) -> iced::Element<'_, Self::Message> {
        //produce the widgets that comprise the user interface
        //interacting with the elements may produce messages which are fed to update
        text("Hello World!").into()
    }
}