import 'package:flutter_bloc/flutter_bloc.dart';

abstract class ${UpdateForm}Event {}

//EVENT-------------------------------------------------------------------------
class ${UpdateForm}EventInit extends ${UpdateForm}Event {
  ${UpdateForm}EventInit();
}

class ${UpdateForm}EventInputForm1 extends ${UpdateForm}Event {
  final String value;

  ${UpdateForm}EventInputForm1(this.value);
}

class ${UpdateForm}EventSelectForm2 extends ${UpdateForm}Event {
  final String value;

  ${UpdateForm}EventSelectForm2(this.value);
}

class ${UpdateForm}EventReset extends ${UpdateForm}Event {}

class ${UpdateForm}EventSubmit extends ${UpdateForm}Event {}

//STATUS------------------------------------------------------------------------
abstract class ${UpdateForm}Status {
  const ${UpdateForm}Status();
}

class ${UpdateForm}StatusInit extends ${UpdateForm}Status {
  const ${UpdateForm}StatusInit();
}

class ${UpdateForm}StatusInitDone extends ${UpdateForm}Status {
  const ${UpdateForm}StatusInitDone();
}

class ${UpdateForm}StatusFillData extends ${UpdateForm}Status {
  final List<String> forWhat;
  final List<dynamic> data;

  const ${UpdateForm}StatusFillData({
    this.forWhat = const [],
    this.data = const [],
  });
}

class ${UpdateForm}StatusOnInput extends ${UpdateForm}Status {
  const ${UpdateForm}StatusOnInput();
}

class ${UpdateForm}StatusLoading extends ${UpdateForm}Status {
  const ${UpdateForm}StatusLoading();
}

class ${UpdateForm}StatusInfo extends ${UpdateForm}Status {
  final String? _title;
  final String? _msg;
  final int? _type;

  //type 1 = success->back
  //type 2 = info->stay
  //type 3 = info->back
  //type 4 = confirm
  //type 5 = snackbar
  //type 6 = action

  String? get title => _title;

  String? get msg => _msg;

  int? get type => _type;

  ${UpdateForm}StatusInfo(this._title, this._msg, this._type);
}

//STATE--------------------------------------------------------------------------
class ${UpdateForm}State {
  final String edForm1;

  final String edForm2;

  ${UpdateForm}Model? data;
  final ${UpdateForm}Status status;

  ${UpdateForm}State({
    this.edForm1 = "",
    this.edForm2 = "",
    this.data,
    this.status = const ${UpdateForm}StatusInit(),
  });

  ${UpdateForm}State copyWith({
    String? edForm1,
    String? edForm2,
    ${UpdateForm}Model? data,
    ${UpdateForm}Status? status,
  }) {
    return ${UpdateForm}State(
      edForm1: edForm1 ?? this.edForm1,
      edForm2: edForm2 ?? this.edForm2,
      data: data ?? this.data,
      status: status ?? this.status,
    );
  }
}

//BLOC--------------------------------------------------------------------------
class ${UpdateForm}Bloc extends Bloc<${UpdateForm}Event, ${UpdateForm}State> {
  final ${UpdateForm}Repo repo;
  final ${UpdateForm}Model arguments;

  ${UpdateForm}Bloc({
    required this.repo,
    required this.arguments,
  }) : super(${UpdateForm}State());

  @override
  Stream<${UpdateForm}State> mapEventToState(${UpdateForm}Event event) async* {
    if (event is ${UpdateForm}EventInit) {
      yield state.copyWith(status: const ${UpdateForm}StatusLoading());
      await Future.delayed(const Duration(seconds: 1));
      ${UpdateForm}Model data = ${UpdateForm}Model(form1: "", form2: "");
      yield state.copyWith(status: ${UpdateForm}StatusInitDone(), data: data);
    } else if (event is ${UpdateForm}EventInputForm1) {
      yield state.copyWith(
        edForm1: event.value,
        status: const ${UpdateForm}StatusFillData(),
      );
    } else if (event is ${UpdateForm}EventSelectForm2) {
      yield state.copyWith(
        edForm2: event.value,
        status: ${UpdateForm}StatusFillData(
          forWhat: ['_form2Controller'],
          data: [event.value],
        ),
      );
    } else if (event is ${UpdateForm}EventSubmit) {
      try {
        yield state.copyWith(status: const ${UpdateForm}StatusLoading());
        await Future.delayed(const Duration(seconds: 1));
        yield state.copyWith(status: ${UpdateForm}StatusInfo("Info", "Success", 3));
      } on Error catch (e) {
        yield state.copyWith(status: ${UpdateForm}StatusInfo("Error", (e.toString()), 2));
      }
    } else if (event is ${UpdateForm}EventReset) {
      yield state.copyWith(
          edForm1: "",
          edForm2: "",
          status: const ${UpdateForm}StatusFillData(
            forWhat: ['_form1Controller','_form2Controller'],
            data: ["",""],
          ));
    }
  }
}
