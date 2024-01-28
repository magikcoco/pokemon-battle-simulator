use iced::widget::{button, Button, Column, Text};
use iced::{executor, Application, Command, Element, Settings, Theme};

fn main() -> iced::Result{
    Simulator::run(Settings::default())
}

struct Simulator {
    hello_world_button_state: button::State,
    second_button_state: button::State
}

#[derive(Debug, Clone)]
enum Message {
    HelloWorldButton,
    SecondButton
}

impl Application for Simulator {
    type Executor = executor::Default;
    type Flags = ();
    type Message = Message;
    type Theme = Theme;

    fn new(_flags: ()) -> (Simulator, Command<Message>) {
        //the state to be in when the porgram starts
        (Simulator {
            hello_world_button_state: button::State::new(),
            second_button_state: button::State::new()
        }, Command::none())
    }

    fn title(&self) -> String {
        //the title of the thing
        String::from("Pokemon Battle Simulator")
    }

    fn update(&mut self, _message: Self::Message) -> Command<Self::Message>{
        //handling messages (events that could change the state of the application)
        //change or update the application in response
        match _message {
            Message::HelloWorldButton => {
                println!("Hello World!");
            }
            Message::SecondButton => {
                println!("Second Button!");
            }
        }
        Command::none()
    }

    fn view(&self) -> Element<Self::Message> {
        //produce the widgets that comprise the user interface
        //interacting with the elements may produce messages which are fed to update
        
        //create a button and add it to the UI
        let hellobutton = Button::new(Text::new("Hello World!")).on_press(Message::HelloWorldButton);
        let secondbutton = Button::new(Text::new("Second button")).on_press(Message::SecondButton);
        //arrange the button
        Column::new()
        .push(hellobutton)
        .push(secondbutton)
        .into()
    }
}