import 'package:flutter_bloc/flutter_bloc.dart';

abstract class ${DetailForm}Event {}

//EVENT-------------------------------------------------------------------------
class ${DetailForm}EventInit extends ${DetailForm}Event {
  ${DetailForm}EventInit();
}

class ${DetailForm}EventInputForm1 extends ${DetailForm}Event {
  final String value;

  ${DetailForm}EventInputForm1(this.value);
}

class ${DetailForm}EventSelectForm2 extends ${DetailForm}Event {
  final String value;

  ${DetailForm}EventSelectForm2(this.value);
}

class ${DetailForm}EventSubmit extends ${DetailForm}Event {}

//STATUS------------------------------------------------------------------------
abstract class ${DetailForm}Status {
  const ${DetailForm}Status();
}

class ${DetailForm}StatusInit extends ${DetailForm}Status {
  const ${DetailForm}StatusInit();
}

class ${DetailForm}StatusInitDone extends ${DetailForm}Status {
  const ${DetailForm}StatusInitDone();
}

class ${DetailForm}StatusFillData extends ${DetailForm}Status {
  final List<String> forWhat;
  final List<dynamic> data;

  const ${DetailForm}StatusFillData({
    this.forWhat = const [],
    this.data = const [],
  });
}

class ${DetailForm}StatusOnInput extends ${DetailForm}Status {
  const ${DetailForm}StatusOnInput();
}

class ${DetailForm}StatusLoading extends ${DetailForm}Status {
  const ${DetailForm}StatusLoading();
}

class ${DetailForm}StatusInfo extends ${DetailForm}Status {
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

  ${DetailForm}StatusInfo(this._title, this._msg, this._type);
}

//STATE--------------------------------------------------------------------------
class ${DetailForm}State {
  final String edForm1;

  final String edForm2;

  ${DetailForm}Model? data;
  final ${DetailForm}Status status;

  ${DetailForm}State({
    this.edForm1 = "",
    this.edForm2 = "",
    this.data,
    this.status = const ${DetailForm}StatusInit(),
  });

  ${DetailForm}State copyWith({
    String? edForm1,
    String? edForm2,
    ${DetailForm}Model? data,
    ${DetailForm}Status? status,
  }) {
    return ${DetailForm}State(
      edForm1: edForm1 ?? this.edForm1,
      edForm2: edForm2 ?? this.edForm2,
      data: data ?? this.data,
      status: status ?? this.status,
    );
  }
}

//BLOC--------------------------------------------------------------------------
class ${DetailForm}Bloc extends Bloc<${DetailForm}Event, ${DetailForm}State> {
  final ${DetailForm}Repo repo;
  final ${DetailForm}Model arguments;

  ${DetailForm}Bloc({
    required this.repo,
    required this.arguments,
  }) : super(${DetailForm}State());

  @override
  Stream<${DetailForm}State> mapEventToState(${DetailForm}Event event) async* {
    if (event is ${DetailForm}EventInit) {
      yield state.copyWith(status: const ${DetailForm}StatusLoading());
      await Future.delayed(const Duration(seconds: 1));
      ${DetailForm}Model data = ${DetailForm}Model(form1: "data 1", form2: "data 2");
      yield state.copyWith(status: ${DetailForm}StatusInitDone(), data: data);

    } else if (event is ${DetailForm}EventInputForm1) {
      yield state.copyWith(
        edForm1: event.value,
        status: const ${DetailForm}StatusFillData(),
      );

    } else if (event is ${DetailForm}EventSelectForm2) {
      yield state.copyWith(
        edForm2: event.value,
        status: ${DetailForm}StatusFillData(
          forWhat: ['_form2Controller'],
          data: [event.value],
        ),
      );

    } else if (event is ${DetailForm}EventSubmit) {
      try {
        yield state.copyWith(status: const ${DetailForm}StatusLoading());
        await Future.delayed(const Duration(seconds: 1));
        yield state.copyWith(status: ${DetailForm}StatusInfo("Info", "Success", 3));

      } on Error catch (e) {
        yield state.copyWith(status: ${DetailForm}StatusInfo("Error", (e.toString()), 2));
      }
    }
  }
}
